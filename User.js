// simple User class, just stores the basic info we need
export class User {
  constructor(userId, username) {
    this.userId = userId;
    this.username = username;

    // keeping track of tasks assigned to this user
    // using an array of task IDs so we don't nest the whole task object inside here
    this.assignedTasks = [];
  }
}