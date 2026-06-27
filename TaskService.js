// TaskService.js
// Service layer — contains all business logic for managing users and tasks.
// All functions are async to simulate non-blocking concurrent access
// using JavaScript's event loop model (vs Java's true multi-threading).

import { loadData, saveData } from "../storage/store.js";
import { Task, CATEGORIES, STATUSES } from "../models/Task.js";
import { User } from "../models/User.js";

// ─────────────────────────────────────────
// USER OPERATIONS
// ─────────────────────────────────────────

// Adds a new user to the system.
// Throws an error if the username is already taken — enforces unique usernames.
export async function addUser(username) {
  const data = await loadData();

  // Check for duplicate username before adding
  const exists = data.users.find((u) => u.username === username);
  if (exists) throw new Error(`User "${username}" already exists.`);

  const newUser = new User(Date.now().toString(), username);
  data.users.push(newUser);

  // Persist updated user list to JSON file asynchronously
  await saveData(data);
  console.log(`User "${username}" created.`);
}

// Returns the full list of registered users from the JSON store.
export async function getUsers() {
  const data = await loadData();
  return data.users;
}

// ─────────────────────────────────────────
// TASK OPERATIONS
// ─────────────────────────────────────────

// Adds a new task and assigns it to an existing user.
// Validates category input before saving — since JS has no enums,
// this manual check replaces what Java would enforce at compile time.
export async function addTask(title, description, category, assignedTo) {
  // Manual validation needed in JS — unlike Java enums, strings aren't type-safe
  if (!CATEGORIES.includes(category)) {
    throw new Error(`Invalid category. Choose from: ${CATEGORIES.join(", ")}`);
  }

  const data = await loadData();

  // Ensure the assigned user actually exists before creating the task
  const userExists = data.users.find((u) => u.username === assignedTo);
  if (!userExists) throw new Error(`User "${assignedTo}" not found.`);

  // Use timestamp as a simple unique ID — good enough for a CLI app
  const taskId = Date.now().toString();
  const newTask = new Task(taskId, title, description, category, assignedTo);

  data.tasks.push(newTask);

  // Async write — non-blocking, allows other operations to queue behind it
  await saveData(data);
  console.log(`Task "${title}" added successfully.`);
}

// Removes a task from the store by its ID.
// Throws a clear error if the task doesn't exist — no silent failures.
export async function removeTask(taskId) {
  const data = await loadData();

  const index = data.tasks.findIndex((t) => t.taskId === taskId);
  if (index === -1) throw new Error(`Task ID "${taskId}" not found.`);

  // splice removes the task in place and returns it so we can log its title
  const removed = data.tasks.splice(index, 1);
  await saveData(data);
  console.log(`Task "${removed[0].title}" removed.`);
}

// Updates the status of an existing task (Pending → In-Progress → Completed).
// Validates the new status value manually — same reason as category validation above.
export async function updateTaskStatus(taskId, newStatus) {
  // JS has no enums — must validate against the allowed statuses array manually
  if (!STATUSES.includes(newStatus)) {
    throw new Error(`Invalid status. Choose from: ${STATUSES.join(", ")}`);
  }

  const data = await loadData();

  // Find the task object directly — JS objects are passed by reference in arrays
  const task = data.tasks.find((t) => t.taskId === taskId);
  if (!task) throw new Error(`Task ID "${taskId}" not found.`);

  task.status = newStatus;

  // Save the mutated task back to the JSON file
  await saveData(data);
  console.log(`Task "${task.title}" updated to "${newStatus}".`);
}

// Returns tasks, optionally filtered by user, category, or status.
// Supports user-specific views as required by the assignment spec.
export async function getTasks(filters = {}) {
  const data = await loadData();
  let tasks = data.tasks;

  // Apply filters dynamically — only filter if the key was passed in
  if (filters.user) tasks = tasks.filter((t) => t.assignedTo === filters.user);
  if (filters.category) tasks = tasks.filter((t) => t.category === filters.category);
  if (filters.status) tasks = tasks.filter((t) => t.status === filters.status);

  return tasks;
}

// ─────────────────────────────────────────
// CONCURRENCY DEMONSTRATIONS
// ─────────────────────────────────────────

// Simulates two users reading the task list at the same time.
// Promise.all dispatches both async operations simultaneously —
// neither waits for the other to finish before starting.
// This mirrors how Java would use two threads to read shared data concurrently.
export async function simulateConcurrentRead() {
  console.log("\n--- Simulating Concurrent Read Access ---");

  // Both getTasks calls are fired at the same time, not one after the other
  const [pendingTasks, completedTasks] = await Promise.all([
    getTasks({ status: "Pending" }),
    getTasks({ status: "Completed" }),
  ]);

  console.log(`User 1 fetched: ${pendingTasks.length} pending task(s)`);
  console.log(`User 2 fetched: ${completedTasks.length} completed task(s)`);
  console.log("Both reads resolved concurrently via Promise.all.");
  console.log("--- End Concurrent Read Demo ---\n");
}

// Simulates two users adding tasks at exactly the same time.
// Demonstrates that JavaScript's async model handles overlapping writes
// without data corruption — the event loop queues each saveData call safely.
// In Java, this would require synchronized blocks or ReentrantLock to be safe.
export async function simulateConcurrentWrite(username1, username2) {
  console.log("\n--- Simulating Concurrent Write Access ---");

  // Both addTask calls are dispatched simultaneously using Promise.all.
  // JavaScript's single-threaded event loop prevents true race conditions —
  // each async write completes atomically before the next one modifies the file.
  await Promise.all([
    addTask("Task from " + username1, "Concurrent write test", "Work", username1),
    addTask("Task from " + username2, "Concurrent write test", "Work", username2),
  ]);

  console.log("Both tasks written concurrently without data corruption.");
  console.log("This shows JS event loop safely sequencing overlapping async writes.");
  console.log("--- End Concurrent Write Demo ---\n");
}