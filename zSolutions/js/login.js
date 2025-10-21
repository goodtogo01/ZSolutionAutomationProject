/* login.js — logic refactored from inline script */
/* Keeps same user list and behavior; redirects to index.html after successful login */

const users = [
    {
      username: "admin",
      password: "test123",
      fullName: "Admin User",
      email: "admin@zsolution.com",
      role: "Administrator"
    },
    {
      username: "shuhan",
      password: "pass123",
      fullName: "Shuhan Shahadat",
      email: "shuhan@zsolution.com",
      role: "Developer"
    },
    {
      username: "miraj",
      password: "hello123",
      fullName: "Shahadat Miraj",
      email: "miraj@zsolution.com",
      role: "Analyst"
    },
    {
      username: "rayhan",
      password: "ray456",
      fullName: "Rayhan Siddiqui",
      email: "rayhan@zsolution.com",
      role: "Tester"
    },
    {
      username: "raja",
      password: "king789",
      fullName: "Saidur Raja",
      email: "raja@zsolution.com",
      role: "Manager"
    },
    // kept original empty-username entry to preserve behavior from your past files
    {
      username: "",
      password: "king789",
      fullName: "Saidur Raja",
      email: "raja@zsolution.com",
      role: "Manager"
    }
  ];
  
  document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.getElementById("loginForm");
    const errorMessage = document.getElementById("error-message");
  
    loginForm.addEventListener("submit", (e) => {
      e.preventDefault();
      errorMessage.textContent = "";
  
      const enteredUsername = document.getElementById("username").value.trim();
      const enteredPassword = document.getElementById("password").value.trim();
  
      const matchedUser = users.find(
        user => user.username === enteredUsername && user.password === enteredPassword
      );
  
      if (matchedUser) {
        localStorage.setItem("loggedIn", "true");
        localStorage.setItem("user", JSON.stringify(matchedUser));
        // Keep redirect target aligned with the new project structure (index.html)
        window.location.href = "index.html";
      } else {
        errorMessage.textContent = "Invalid username or password!";
      }
    });
  });