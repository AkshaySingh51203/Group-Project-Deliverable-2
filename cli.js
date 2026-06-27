// this is the main CLI file, handles everything the user sees and types
// using readline-sync because it's way simpler than dealing with async input

import readlineSync from "readline-sync";
import { addUser, getUsers, addTask, removeTask, updateTaskStatus, getTasks, simulateConcurrentRead, simulateConcurrentWrite } from "../services/TaskService.js";
import { CATEGORIES, STATUSES } from "../models/Task.js";

export async function runCLI() {
  console.log("\n=== Collaborative To-Do List (JavaScript) ===");
  let running = true;

  while (running) {
    // print the menu every loop so the user always knows their options
    console.log(`
1.  Add User
2.  View Users
3.  Add Task
4.  View All Tasks
5.  Filter Tasks
6.  Update Task Status
7.  Remove Task
8.  Demo Concurrent Read
9.  Demo Concurrent Write
10. Exit
    `);

    const choice = readlineSync.question("Choose an option: ").trim();

    // wrapping everything in try/catch so the app doesn't crash on bad input
    // any error thrown in the service layer bubbles up and gets caught here
    try {
      if (choice === "1") {
        const username = readlineSync.question("Enter username: ").trim();
        await addUser(username);

      } else if (choice === "2") {
        // just fetch and print all users, nothing fancy
        const users = await getUsers();
        if (users.length === 0) {
          console.log("No users found.");
        } else {
          users.forEach((u) => console.log(`- ${u.username} (ID: ${u.userId})`));
        }

      } else if (choice === "3") {
        // collect all the info we need before creating the task
        const title = readlineSync.question("Task title: ").trim();
        const description = readlineSync.question("Description: ").trim();
        console.log("Categories:", CATEGORIES.join(", "));
        const category = readlineSync.question("Category: ").trim();
        const assignedTo = readlineSync.question("Assign to (username): ").trim();
        await addTask(title, description, category, assignedTo);

      } else if (choice === "4") {
        const tasks = await getTasks();
        printTasks(tasks);

      } else if (choice === "5") {
        // let the user pick what they want to filter by
        // the [filterBy] syntax lets us pass the key dynamically which is pretty handy
        const filterBy = readlineSync.question("Filter by (user/category/status): ").trim();
        const value = readlineSync.question(`Enter ${filterBy}: `).trim();
        const tasks = await getTasks({ [filterBy]: value });
        printTasks(tasks);

      } else if (choice === "6") {
        // ask for the task ID first, then show valid statuses so the user knows what to type
        const taskId = readlineSync.question("Enter Task ID to update: ").trim();
        console.log("Statuses:", STATUSES.join(", "));
        const newStatus = readlineSync.question("New status: ").trim();
        await updateTaskStatus(taskId, newStatus);

      } else if (choice === "7") {
        const taskId = readlineSync.question("Enter Task ID to remove: ").trim();
        await removeTask(taskId);

      } else if (choice === "8") {
        // runs the concurrent read demo using Promise.all
        await simulateConcurrentRead();

      } else if (choice === "9") {
        // need at least 2 users for this to make sense
        // just grab the first two users that are already registered
        const users = await getUsers();
        if (users.length < 2) {
          console.log("You need at least 2 users for this demo. Please add more users first.");
        } else {
          await simulateConcurrentWrite(users[0].username, users[1].username);
        }

      } else if (choice === "10") {
        console.log("Goodbye!");
        running = false;

      } else {
        console.log("Invalid option, try again.");
      }

    } catch (err) {
      // print the error message so the user knows what went wrong
      // without this the whole app would just crash which isn't great
      console.error("Error:", err.message);
    }
  }
}

// pulled this out into its own function to keep the menu logic clean
// gets called whenever we need to display a list of tasks
function printTasks(tasks) {
  if (tasks.length === 0) {
    console.log("No tasks found.");
    return;
  }
  tasks.forEach((t) => {
    console.log(`\n[${t.taskId}] ${t.title}`);
    console.log(`  Description : ${t.description}`);
    console.log(`  Status      : ${t.status}`);
    console.log(`  Category    : ${t.category}`);
    console.log(`  Assigned to : ${t.assignedTo}`);
    console.log(`  Created at  : ${t.createdAt}`);
  });
}