// ----------------------------------------------------
// 🌐 ZSolution Backend Server
// ----------------------------------------------------

const express = require('express');
const cors = require('cors');
const morgan = require('morgan');
const fs = require('fs');
const path = require('path');

// Initialize Express
const app = express();
const PORT = process.env.PORT || 5000;

// ----------------------------------------------------
// 🧩 Middleware Setup
// ----------------------------------------------------
app.use(express.json());               // Parse JSON bodies
app.use(cors());                       // Allow cross-origin requests
app.use(morgan('dev'));                // Log HTTP requests

// ----------------------------------------------------
// 📁 Ensure Data Directory Exists
// ----------------------------------------------------
const dataDir = path.join(__dirname, 'data');
const usersFile = path.join(dataDir, 'users.json');

// If /data folder or users.json doesn’t exist, create it
if (!fs.existsSync(dataDir)) {
  fs.mkdirSync(dataDir);
  console.log('📁 Created missing "data" directory.');
}

if (!fs.existsSync(usersFile)) {
  fs.writeFileSync(usersFile, '[]', 'utf8');
  console.log('🆕 Created empty users.json file.');
}

// ----------------------------------------------------
// 👥 Import User Routes
// ----------------------------------------------------
const userRoutes = require('./routes/users');

// Use the routes under /api/users
app.use('/api/users', userRoutes);

// ----------------------------------------------------
// 🏠 Default Root Route
// ----------------------------------------------------
app.get('/', (req, res) => {
  res.send('✅ ZSolution Backend Server is running...');
});

// ----------------------------------------------------
// 🚀 Start Server
// ----------------------------------------------------
app.listen(PORT, () => {
  console.log(`🔥 Server running at: http://localhost:${PORT}`);
});