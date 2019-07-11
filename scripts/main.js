var listOfALevels = ['Accounting', 'Afrikaans', 'Ancient History', 'Anthropology', 'Applied Art and Design', 'Applied Business', 'Applied ICT', 'Applied Science', 'Arabic', 'Archaeology', 'Art and Design', 'Bengali', 'Biblical Hebrew', 'Biology', 'Business', 'Business Studies', 'Chemistry', 'Chinese', 'Citizenship Studies', 'Classical Civilisation', 'Classical Greek', 'Classical Studies', 'Communication and Culture', 'Computer Science', 'Computing', 'Creative Writing', 'Critical Thinking', 'Dance', 'Design and Technology', 'Design and Textiles', 'Digital Media and Design', 'Digital Technology', 'Divinity', 'Drama', 'Drama and Theatre', 'Dutch', 'Economics', 'Economics and Business', 'Electronics', 'Engineering', 'English Language', 'English Language and Literature', 'English Literature', 'Environmental Science', 'Environmental Studies', 'Environmental Technology', 'Fashion and Textiles', 'Film Studies', 'Food Studies', 'Food Technology', 'French', 'Further Mathematics', 'General Studies', 'Geography', 'Geology', 'German', 'Global Development', 'Global Perspectives and Research', 'Government and Politics', 'Greek', 'Gujarati', 'Health and Social Care', 'Hindi', 'Hinduism', 'History', 'History of Art', 'Home Economics', 'Human Biology', 'Humanities', 'ICT', 'Information Technology', 'Irish', 'Islamic Studies', 'Italian', 'Japanese', 'Journalism', 'Latin', 'Law', 'Leisure Studies', 'Life and Health Sciences', 'Marine Science', 'Mathematics', 'Media Studies', 'Modern Hebrew', 'Modern Languages', 'Moving Image Arts', 'Music', 'Music Technology', 'Nutrition and Food Science', 'Panjabi', 'Performance Studies', 'Performing Arts', 'Persian', 'Philosophy', 'Photography', 'Physical Education', 'Physical Science', 'Physics', 'Polish', 'Politics', 'Portuguese', 'Product Design', 'Professional Business Services', 'Psychology', 'Pure Mathematics', 'Quantitative Methods', 'Religious Studies', 'Russian', 'Science in Society', 'Sociology', 'Software Systems Development', 'Spanish', 'Sports Science', 'Statistics', 'Systems and Control Technology', 'Tamil', 'Technology and Design', 'Thinking Skills', 'Travel and Tourism', 'Turkish', 'Urdu', 'Welsh', 'World Development']

var listOfGrades = [
  'A*',
  'A',
  'B',
  'C',
  'D',
  'E'
];

var listOfCourses = [
  'Chemistry',
  'Civil Engineering',
  'Computer Science',
  'Economics',
  'History',
  'Marketing',
  'Mathematics',
  'Physics',
  'Psychology'
];

var counter = 0;

function createALevel() {
  var alevel = $('<div class="alevel input-group row c-' + counter + '"></div>');
  var subjectSelect = $('<select class="custom-select col-8 subject-select"></select>');
  subjectSelect.append($('<option selected>Subject</option>'));
  for (var i = 0; i < listOfALevels.length; i++) {
    subjectSelect.append($('<option value="' + listOfALevels[i] + '">' + listOfALevels[i] + '</option>'));
  }
  var gradeSelect = $('<select class="custom-select col-2 grade-select"></select>');
  gradeSelect.append($('<option selected>Predicted grade</option>'));
  for (var i = 0; i < listOfGrades.length; i++) {
    gradeSelect.append($('<option value="' + listOfGrades[i] + '">' + listOfGrades[i] + '</option>'));
  }
  var removeSubject = $('<button class="btn btn-danger col-1 offset-1 remove-subject" onclick="removeALevel(' + counter + ')">Remove</button>');
  alevel.append(subjectSelect);
  alevel.append(gradeSelect);
  alevel.append(removeSubject);
  $('.alevel-container').append(alevel);
  $('.show-prefs').removeClass('hidden');
  counter += 1;
}

function removeALevel(c) {
  $('.c-' + c).remove();
  if ($('.alevel').length == 0) {
    $('.show-prefs').addClass('hidden');
  }
}

function setRankings(unis) {
  var rankingTable = $('#ranking-table');
  for (var i = 0; i < unis.length; i++) {
    var uni = unis[i];
    var card = $('<div class="card"></div>');
    var cardHeader = $('<div class="card-header" id="heading-' + uni['id'] + '"><h2 class="mb-0"><button class="btn btn-link collapsed ranking-table-uni-name" type="button" data-toggle="collapse" data-target="#collapse-' + uni['id'] + '" aria-expanded="false" aria-controls="#collapse-' + uni['id'] + '">' + (i + 1) + '. <img class="ranking-table-uni-logo" alt="" src="resources/' + uni['id'] + '.png">' + uni['name'] + '</button></h2></div>');
    card.append(cardHeader);
    var collapsibleBody = $('<div id="collapse-' + uni['id'] + '" class="collapse" aria-labelledby="heading' + uni['id'] + '" data-parent="#ranking-table"></div>');
    var cardBody = $('<div class="card-body">' + uni['info'] + '</div>');
    collapsibleBody.append(cardBody);
    card.append(collapsibleBody);
    rankingTable.append(card);
  }
}

function showCourse() {
  location.href = 'course.php';
}

function setCourses() {
  var courses = $('.courses');
  for (var i = 0; i < listOfCourses.length; i++) {
    courses.append($('<option>' + listOfCourses[i] + '</option>'));
  }
}

function showPrefs() {
  location.href = 'preferences.php';
}

function send() {
  var prefs = $('.custom-range');
  var s = '';
  for (var i = 0; i < prefs.length; i++) {
    var pref = prefs[i];
    var classes = pref.className.split(' ');

    for (var j = 0; j < classes.length; j++) {
      if (classes[j] != 'custom-range' && classes[j] != 'mb-1') {
        var metric = classes[j];
      }
    }
    var val = pref.value;
    s += metric + '|' + val + ',';
  }
  console.log(s);
  location.href = 'results.php';
}
