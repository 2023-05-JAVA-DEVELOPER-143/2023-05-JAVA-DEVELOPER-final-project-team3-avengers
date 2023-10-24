document.addEventListener('DOMContentLoaded', function () {
    const categoryList = document.getElementById('category-list');
    const subcategoryList = document.getElementById('subcategory-list');
    const optionList = document.getElementById('option-list');
  
    categoryList.addEventListener('click', function (event) {
      const categoryId = event.target.dataset.categoryId;
  
      // Simulate fetching subcategories based on the selected category
      // Replace the code below with your actual logic to fetch and populate subcategories
  
      // Clear the current subcategory list
      subcategoryList.innerHTML = '';
  
      // Add the new subcategory items
      for (let i = 1; i <= 3; i++) {
        const subcategoryItem = document.createElement('li');
        subcategoryItem.textContent = `Subcategory ${i}`;
        subcategoryList.appendChild(subcategoryItem);
      }
    });
  
    subcategoryList.addEventListener('click', function (event) {
      const subcategoryName = event.target.textContent;
  
      // Simulate fetching options based on the selected subcategory
      // Replace the code below with your actual logic to fetch and populate options
  
      // Clear the current option list
      optionList.innerHTML = '';
  
      // Add the new option items
      for (let i = 1; i <= 5; i++) {
        const optionRow = document.createElement('tr');
        const optionName = document.createElement('td');
        const optionValue = document.createElement('td');
  
        optionName.textContent = `Option ${i}`;
        optionValue.textContent = `Value ${i}`;
  
        optionRow.appendChild(optionName);
        optionRow.appendChild(optionValue);
  
        optionList.appendChild(optionRow);
      }
    });
  });
  