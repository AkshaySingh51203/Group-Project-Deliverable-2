// these are the only categories we're allowing for now
// might add more later if needed
export const CATEGORIES = ["Work", "Personal", "School", "Health", "Other"];

// three statuses felt like enough to track progress without overcomplicating it
export const STATUSES = ["Pending", "In-Progress", "Completed"];

export class Task {
  constructor(taskId, title, description, category, assignedTo) {
    this.taskId = taskId;
    this.title = title;
    this.description = description;

    // every task starts as Pending when it's first created
    this.status = "Pending";

    this.category = category;

    // storing the username instead of the whole user object keeps things simple
    this.assignedTo = assignedTo;

    // saving the timestamp so we know when the task was created
    this.createdAt = new Date().toISOString();
  }
}