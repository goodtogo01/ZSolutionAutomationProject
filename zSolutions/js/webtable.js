// webtable.js — handles dynamic employee table add/delete with localStorage persistence

const tableBody = document.getElementById("tableBody");
const msgBox = document.getElementById("table-message");
const STORAGE_KEY = "zsolutionRecords";

// Load saved records
let records = JSON.parse(localStorage.getItem(STORAGE_KEY)) || [];

function saveRecords() {
  localStorage.setItem(STORAGE_KEY, JSON.stringify(records));
}

function showMessage(msg, type = "success") {
  msgBox.innerText = msg;
  msgBox.style.display = "block";
  msgBox.style.backgroundColor = type === "success" ? "#d1f0ea" : "#f8d7da";
  msgBox.style.color = type === "success" ? "#155724" : "#721c24";
  setTimeout(() => (msgBox.style.display = "none"), 3000);
}

function generateInitialRecords() {
  if (records.length === 0) {
    const initialData = [
      { name: "John Doe", role: "Developer", location: "USA" },
      { name: "Jane Smith", role: "Designer", location: "UK" },
      { name: "Mike Johnson", role: "Manager", location: "Canada" },
      { name: "Emily Davis", role: "QA", location: "India" },
      { name: "Robert Brown", role: "Lead", location: "Germany" },
      { name: "Linda Wilson", role: "Analyst", location: "Japan" },
      { name: "James Taylor", role: "Support", location: "Australia" },
      { name: "Patricia Miller", role: "Architect", location: "UAE" },
      { name: "Michael Anderson", role: "Developer", location: "USA" },
      { name: "Delbert Sanford", role: "Designer", location: "UAE" }
    ];
    initialData.forEach(item =>
      records.push({ id: Date.now() + Math.random(), ...item })
    );
    saveRecords();
  }
  renderTable();
}

function renderTable() {
  tableBody.innerHTML = "";
  records.forEach((rec, index) => {
    const row = document.createElement("tr");
    row.innerHTML = `
      <td>${index + 1}</td>
      <td>${rec.name}</td>
      <td>${rec.role}</td>
      <td>${rec.location}</td>
      <td><button onclick="deleteRecord(${rec.id})">Delete</button></td>
    `;
    tableBody.appendChild(row);
  });
}

function addNewRecord() {
  const name = document.getElementById("newName").value.trim();
  const role = document.getElementById("newRole").value.trim();
  const location = document.getElementById("newLocation").value.trim();

  if (!name || !role || !location) {
    showMessage("Please fill out all fields.", "error");
    return;
  }

  records.push({ id: Date.now() + Math.random(), name, role, location });
  saveRecords();
  renderTable();
  showMessage("New record added successfully!");

  document.getElementById("newName").value = "";
  document.getElementById("newRole").value = "";
  document.getElementById("newLocation").value = "";
}

function deleteRecord(id) {
  records = records.filter(rec => rec.id !== id);
  saveRecords();
  renderTable();
  showMessage("Record deleted successfully!");
}

// Initialize
generateInitialRecords();