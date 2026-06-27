import { readFile, writeFile } from "fs/promises";

const FILE_PATH = "./data.json";

export async function loadData() {
  try {
    const raw = await readFile(FILE_PATH, "utf-8");
    return JSON.parse(raw);
  } catch (err) {
    return { tasks: [], users: [] };
  }
}

export async function saveData(data) {
  try {
    await writeFile(FILE_PATH, JSON.stringify(data, null, 2), "utf-8");
  } catch (err) {
    console.error("Error saving data:", err.message);
  }
}
