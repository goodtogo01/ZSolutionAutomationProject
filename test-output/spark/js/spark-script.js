document.addEventListener("DOMContentLoaded", function() {
  console.log("ZSolution Custom Extent Report Loaded ✅");

  // Change document title dynamically
  document.title = "ZSolution | Automation Report";

  // Add a watermark
  const watermark = document.createElement("div");
  watermark.innerText = "ZSolution Confidential";
  watermark.style.position = "fixed";
  watermark.style.bottom = "10px";
  watermark.style.right = "20px";
  watermark.style.opacity = "0.15";
  watermark.style.fontSize = "20px";
  watermark.style.color = "#fff";
  document.body.appendChild(watermark);
});