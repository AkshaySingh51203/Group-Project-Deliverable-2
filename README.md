# Collaborative To-Do List — JavaScript Implementation

## Overview
A Node.js CLI application that allows multiple users to manage tasks collaboratively.
Demonstrates JavaScript-specific features including async/await, Promises, and JSON-native data storage.

## Requirements
- Node.js v18 or higher
- npm

## Installation
git clone <your-repo-url>
cd todo-js
npm install

## Run
node index.js

## Features
- Add and manage multiple users
- Create tasks with title, description, category, and assigned user
- Categories: Work, Personal, School, Health, Other
- Statuses: Pending, In-Progress, Completed
- Filter tasks by user, category, or status
- Simulated concurrent access using Promise.all
- JSON file persistence via fs/promises

## Project Structure
- models/     → User and Task class definitions
- services/   → Business logic and async task operations  
- storage/    → JSON file read/write using fs/promises
- ui/         → CLI menu and user interaction
- data.json   → Local data store
- index.js    → Entry point

## Language-Specific Features Demonstrated
- async/await for non-blocking operations
- Promise.all for simulated concurrent access
- JSON.parse / JSON.stringify for native data persistence
- ES Modules (import/export)
- Dynamic typing with manual input validation