const express = require('express');
const fs = require('fs');
const path = require('path');

const router = express.Router();

// Path to local JSON file (acting as a simple database)
const dataFile = path.join(__dirname, '../data/users.json');

/* ------------------------------------------------------------------
   Utility Functions
------------------------------------------------------------------ */

// Read all users from JSON file
const getUsers = () => {
  try {
    const data = fs.readFileSync(dataFile, 'utf8');
    return JSON.parse(data);
  } catch (err) {
    console.error("❌ Error reading users.json:", err.message);
    return [];
  }
};

// Save updated users list back to JSON file
const saveUsers = (data) => {
  try {
    fs.writeFileSync(dataFile, JSON.stringify(data, null, 2));
  } catch (err) {
    console.error("❌ Error writing users.json:", err.message);
  }
};

/* ------------------------------------------------------------------
   ROUTES
------------------------------------------------------------------ */

// ✅ Get all users
router.get('/', (req, res) => {
  const users = getUsers();
  res.json(users);
});

// 🔐 Login (Verify username and password)
router.post('/login', (req, res) => {
  console.log("📥 Login attempt received:", req.body);

  const { username, password } = req.body;
  if (!username || !password) {
    return res.status(400).json({ message: 'Username and password are required.' });
  }

  const users = getUsers();
  const user = users.find(u => u.username === username && u.password === password);

  if (user) {
    // Remove password before sending back to client (for safety)
    const { password: _, ...safeUser } = user;
    res.json(safeUser);
  } else {
    res.status(401).json({ message: 'Invalid username or password.' });
  }
});

// ➕ Create (Add) a new user
router.post('/', (req, res) => {
  const { username, password, fullName, email, role } = req.body;

  if (!username || !password) {
    return res.status(400).json({ message: 'Username and password are required.' });
  }

  const users = getUsers();

  // Prevent duplicate usernames
  if (users.some(u => u.username === username)) {
    return res.status(409).json({ message: 'Username already exists.' });
  }

  const newUser = {
    id: Date.now(),
    username,
    password,
    fullName: fullName || '',
    email: email || '',
    role: role || 'User'
  };

  users.push(newUser);
  saveUsers(users);

  res.status(201).json({ message: 'User created successfully.', user: newUser });
});

// ✏️ Update existing user
router.put('/:id', (req, res) => {
  const userId = parseInt(req.params.id);
  const updates = req.body;

  let users = getUsers();
  const index = users.findIndex(u => u.id === userId);

  if (index === -1) {
    return res.status(404).json({ message: 'User not found.' });
  }

  users[index] = { ...users[index], ...updates };
  saveUsers(users);

  res.json({ message: 'User updated successfully.', user: users[index] });
});

// ❌ Delete user
router.delete('/:id', (req, res) => {
  const userId = parseInt(req.params.id);
  const users = getUsers();
  const remainingUsers = users.filter(u => u.id !== userId);

  if (users.length === remainingUsers.length) {
    return res.status(404).json({ message: 'User not found.' });
  }

  saveUsers(remainingUsers);
  res.json({ message: 'User deleted successfully.' });
});

module.exports = router;