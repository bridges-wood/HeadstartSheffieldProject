<!DOCTYPE html>
<html lang="en">
<head>
 <meta charset="UTF-8">
 <meta name="viewport" content="width=device-width, initial-scale=1.0">
 <meta http-equiv="X-UA-Compatible" content="ie=edge">
 <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
 <link rel="stylesheet" href="styles/main.css">
 <title>MyRanker</title>
</head>
<body>
<ul class="pagination justify-content-center">
<h1>Preferences</h1>
</ul>
<ul class="pagination justify-content-center">
<p><i>Please use the sliders to rank each criterion, depending how important they are to you.</i></p>
</ul>
<b>League Table Rankings</b>
<input type="range" class="custom-range" min="1" max="10" step="1" class="league-table-rankings">
<br><br>
<b>Student Satisfaction</b>
<input type="range" class="custom-range" min="1" max="10" step="1" class="student-satisfaction">
<br><br>
<b>Employability</b>
<input type="range" class="custom-range" min="1" max="10" step="1" class="employability">
<br><br>
<b>Research Quality</b>
<input type="range" class="custom-range" min="1" max="10" step="1" class="research-quality">
<br><br>
<b>Student-to-Staff Ratio</b>
<input type="range" class="custom-range" min="1" max="10" step="1" class="sts-ratio">
<br><br>
<b>Cost of Living</b>
<input type="range" class="custom-range" min="1" max="10" step="1" class="cost-of-living">
<br><br>
<b>International Student Ratio</b>
<input type="range" class="custom-range" min="1" max="10" step="1" class="international-student-ratio">
<br><br>
<label for="customRange3">Campus</label><label for="customRange3">City</label>
<input type="range" class="custom-range" min="1" max="10" step="1" id="customRange3">
<label for="customRange3"></label>Does not Matter<label for="customRange3">  Only Russel Group</label>
<input type="range" class="custom-range" min="1" max="10" step="1" id="customRange3">
<label for="customRange3">International Student Ratio</label>
<input type="range" class="custom-range" min="1" max="10" step="1" id="customRange3">
<ul class="pagination justify-content-center">
<button type="button" class="btn btn-danger">Get Your Personalised List!</button>
</ul>
<nav aria-label="Page navigation example">
 <ul class="pagination justify-content-center">
   <li class="page-item disabled">
     <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Previous</a>
   </li>
   <li class="page-item"><a class="page-link" href="#">1</a></li>
   <li class="page-item"><a class="page-link" href="#">2</a></li>
   <li class="page-item">
     <a class="page-link" href="#">Next</a>
   </li>
 </ul>
</nav>
 <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
 <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
 <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
 <script src="scripts/main.js"></script>
</body>
