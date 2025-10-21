// main.js — core logic for tab navigation, user display, forms, and broken links

window.onload = function () {
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      const user = JSON.parse(storedUser);
      document.getElementById("username-display").textContent = `Welcome, ${user.fullName}`;
      document.getElementById("display-username").textContent = user.fullName;
      document.getElementById("display-useremail").textContent = user.email || "Not available";
      document.getElementById("display-userrole").textContent = user.role || "User";
    } else {
      window.location.href = "login.html";
    }
  };
  
  function logout() {
    localStorage.clear();
    window.location.href = "login.html";
  }
  
  function showTab(tabId) {
    document.querySelectorAll(".tab-content").forEach(tab => tab.classList.remove("active"));
    document.getElementById(tabId).classList.add("active");
  }
  
  /* Textbox Submission */
  function showResult() {
    document.getElementById("result-name").textContent = document.getElementById("name").value;
    document.getElementById("result-email").textContent = document.getElementById("email").value;
    document.getElementById("result-address").textContent = document.getElementById("address").value;
    document.getElementById("result").style.display = "block";
  }
  
  /* Radio Button */
  function submitRadio() {
    const gender = document.querySelector('input[name="gender"]:checked');
    const age = document.querySelector('input[name="age"]:checked');
    if (gender && age) {
      document.getElementById("selected-gender").textContent = gender.value;
      document.getElementById("selected-age").textContent = age.value;
      document.getElementById("radio-result").style.display = "block";
    } else {
      alert("Please select both gender and age group.");
    }
  }
  
  /* Checkbox */
  document.querySelectorAll('input[name="hobby"]').forEach(cb => {
    cb.addEventListener("change", () => {
      document.querySelectorAll('input[name="hobby"]').forEach(el => { if (el !== cb) el.checked = false; });
    });
  });
  
  document.querySelectorAll('input[name="subscribe"]').forEach(cb => {
    cb.addEventListener("change", () => {
      document.querySelectorAll('input[name="subscribe"]').forEach(el => { if (el !== cb) el.checked = false; });
    });
  });
  
  function submitCheckbox() {
    const hobby = document.querySelector('input[name="hobby"]:checked');
    const subscribe = document.querySelector('input[name="subscribe"]:checked');
    document.getElementById("selected-hobby").textContent = hobby ? hobby.value : "None";
    document.getElementById("selected-subscribe").textContent = subscribe ? subscribe.value : "None";
    document.getElementById("checkbox-result").style.display = "block";
  }
  
  /* Dropdown */
  function submitDropdown() {
    const country = document.getElementById("country").value;
    const city = document.getElementById("city").value;
    if (country && city) {
      document.getElementById("selected-country").textContent = country;
      document.getElementById("selected-city").textContent = city;
      document.getElementById("dropdown-result").style.display = "block";
    } else {
      alert("Please select both country and city.");
    }
  }
  
  /* Broken Links */
  function checkBrokenLink(url) {
    fetch(url)
      .then(response => {
        const resultDiv = document.getElementById("broken-link-result");
        resultDiv.style.display = "block";
        if (response.status >= 400 && response.status < 500) {
          resultDiv.innerHTML = `🔴 <strong>Client Error:</strong> ${url} returned status <strong>${response.status}</strong>.`;
        } else if (response.status >= 500) {
          resultDiv.innerHTML = `🔴 <strong>Server Error:</strong> ${url} returned status <strong>${response.status}</strong>.`;
        } else {
          resultDiv.innerHTML = `✅ <strong>Success:</strong> ${url} returned status <strong>${response.status}</strong>.`;
        }
      })
      .catch(error => {
        const resultDiv = document.getElementById("broken-link-result");
        resultDiv.style.display = "block";
        resultDiv.innerHTML = `❌ <strong>Fetch Failed:</strong> Could not reach ${url}. <br><small>${error.message}</small>`;
      });
  }