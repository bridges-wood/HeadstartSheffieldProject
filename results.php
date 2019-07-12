<?php
  $host = 'localhost';
  $dbname = 'myranker';
  $username = 'myranker';
  $password = 'myranker';

  $conn = new mysqli($host, $username, $password, $dbname);

  function checkIfPermalinkExists($conn, $p) {
    $sql = 'SELECT permalink, info
             FROM main
             WHERE permalink=\'' . $p . '\'';

    $result = $conn->query($sql);


    while ($row = $result->fetch_assoc()) {
      $result->free();
      return true;
    }

    $result->free();
    return false;
  }

  function setPermalink($conn, $permalink, $s) {
    $stmt = $conn->prepare("INSERT INTO main (permalink, info) VALUES (?, ?)");
    $stmt->bind_param("ss", $p, $info);
    $p = $permalink;
    $info = $s;
    $stmt->execute();
  }

  function getInfoFromPermalink($conn, $permalink) {
    $sql = 'SELECT info
             FROM main
             WHERE permalink=\'' . $permalink . '\'';

    $result = $conn->query($sql);


    while ($row = $result->fetch_assoc()) {
      return $row['info'];
    }
    return false;
  }

  function generateRandomString($length = 10) {
    // https://stackoverflow.com/questions/4356289/php-random-string-generator
    $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    $charactersLength = strlen($characters);
    $randomString = '';
    for ($i = 0; $i < $length; $i++) {
      $randomString .= $characters[rand(0, $charactersLength - 1)];
    }
    return $randomString;
  }

  if (!isset($_GET['r'])) {
    $permalink = generateRandomString();
    while (checkIfPermalinkExists($conn, $permalink)) {
      $permalink = generateRandomString();
    }
    setPermalink($conn, $permalink, $_GET['bc']);
    header('Location: results.php?r=' . $permalink);
    die();
  }

  $permalink = $_GET['r'];

  $bc = getInfoFromPermalink($conn, $_GET['r']);

  // if (!$bc) {
  //   header('Location: alevels.php');
  //   die();
  // }

  $fp = stream_socket_client("tcp://localhost:7000", $errno, $errstr, 5);
  if (!$fp) {
      echo "$errstr ($errno)<br />\n";
  } else {
    fwrite($fp, $bc . "\n");
    $c = fgets($fp, 500);
    fclose($fp);
    $c = substr($c, 2);
    $d = '';
    while ($c[0] != ']') {
      $d .= $c[0];
      $c = substr($c, 1);
    }
    $d .= ']';

  }

  $conn->close();



 ?>





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
  <div class="heading text-center">
    <div>
      <img class="logo" src="resources/myranker-logo.png" alt="MyRanker Logo">
    </div>
    <h1>Your Personalised Rankings</h1>
    <p>Click on any individual university to reveal more information.</p>
    <br>
    <br>
    <p style="width: 100%;"><span>Use this link to come back to your results at any time:</span> <div class="permalink-display">https://myranker.co.uk/results.php?r=<?php echo $permalink; ?></div></p>
    <br>
    <br>
    <h3>Share your results!</h3>
    <div class="share-buttons">
      <div>
        <button class="btn btn-info twitter" onclick="tweet('<?php echo $permalink; ?>');">Twitter</button>
      </div>
      <div>
        <button class="btn btn-primary facebook" onclick="facebook('<?php echo $permalink; ?>');">Facebook</button>
      </div>
    </div>
  </div>

  <div class="container main">
    <div class="accordion" id="ranking-table"></div>
  </div>
  <br>
  <br>


  <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
  <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
  <script src="scripts/main.js"></script>
  <script>

    var unis = [
      {
        "id": "cambridge",
        "name": "University of Cambridge",
        "info": "The Cambridge course is broad and deep - providing you with skills highly prized in industry and for research, and giving skills to create future technology. All aspects of modern computer science are covered, along with the underlying theory and foundations in economics, law and business. You also develop practical skills, such as programming and hardware systems. The course offers an optional fourth year leading to an MEng degree (progression to the fourth year is dependent on satisfactory performance)."

      },
      {
        "id": "st_andrews",
        "name": "University of St Andrews",
        "info": "Over the last 600 years, the University of St Andrews has established a reputation as one of the world’s leading teaching centres. Today, we offer a flexible degree structure based on your choice of subject specialism, creating an environment which nurtures inquisitive minds and a culture of shared learning."
      },
      {
        "id": "hull",
        "name": "University of Hull",
        "info": "This programme offers an inspiring combination of computer science and video game development. We'll give you a grounding in computer science, set within the context of game programming – concentrating on programming, simulation, interactive real-time graphics and artificial intelligence. - Accredited by the British Computer Society (the Chartered Institute for IT) to meet the requirements for full Chartered IT Professional and partial Chartered Engineer status."
      },
      {
        "id": "sheffield",
        "name": "University of Sheffield",
        "info": "Computer science at Sheffield is a rare blend of advanced science, business experience and creativity. This is the only computer science department in the UK with its own student-run software company – Genesys Solutions. You will get the chance to work for Genesys as part of your course. Teaching staff are experts in iPhone, Android and VR technology. Guest lecturers come from IBM, Microsoft and HP. Artificial intelligence is developing fast. As the world tries to keep up, experts are in demand. Sheffield is a great place to learn about AI because we encourage a deep understanding of biologically inspired algorithms, their relationship to biological intelligence and the nature of consciousness itself."
      },
      {
        "id": "imperial",
        "name": "Imperial College London",
        "info": "With the spread of computing procedures and mathematical ideas into many areas, there is high demand for professionals who are expert in both. Our Mathematics and Computer Science degrees are mathematical courses orientated towards computing science. Taught jointly by the Departments of Computing and Mathematics, they provide a firm foundation in mathematics, particularly in pure mathematics, numerical analysis and statistics."

      },
      {
        "id": "bournemouth",
        "name": "Bournemouth University",
        "info": "Over the last 600 years, the University of St Andrews has established a reputation as one of the world’s leading teaching centres. Today, we offer a flexible degree structure based on your choice of subject specialism, creating an environment which nurtures inquisitive minds and a culture of shared learning."
      },
      {
        "id": "surrey",
        "name": "University of Surrey",
        "info": "Our BSc Computer Science course will develop your understanding of the concepts and principles that underpin computing systems.We will provide you with the balance of knowledge and skills you need to design, implement and troubleshoot software and complex systems to a professional standard.You will be provided with opportunities to apply theory to real-world scenarios and may – like many of our students – choose to take a Professional Training placement year, giving you invaluable industry experience."

      },
      {
        "id": "chester",
        "name": "University of Chester",
        "info": "Throughout our course you will explore how computers work, how computer software is developed, how computers communicate with each other, and the ways in which software manipulates, stores and processes data. You will work effectively in teams, working on diverse projects, which is the most important skill that employers require."
      },
      {
        "id": "loughborough",
        "name": "Loughborough University",
        "info": "Computer Scientists need a diverse set of practical and theoretical skills. Our BSC Computer Science degree provides you with a strong foundation in the critical areas of computer science while also giving you the opportunity to tailor the degree to your own strengths, interests and career aspirations through an optional placement year and specialised modules and project work taught and supervised by experts in their respective fields."
      },
      {
        "id": "aberystwyth",
        "name": "Aberystwyth University",
        "info": "Computer Science covers a vast range of topics including programming, computer operating systems, software design, and the engineering of large software systems; meaning it is our Department’s most flexible degree, providing core modules that are key for a career in Computer Science. On our **Chartered Institute for IT (BCS) accredited course** you will acquire specialist skills such as software engineering, graphics and visualisation, artificial intelligence, robotics, telematics, mobile computing and open source computing, that are highly sought out by employers in this industry. The prestigious MComp in Computer Science issimilar to the Bachelor's degree but provides an extra year of instruction in the form of and integrated Masters"
      }
    ]
;

      var u = <?php echo $d; ?>;
      var v = [];

      for (var i = 0; i < u.length; i++) {
        for (var j = 0; j < unis.length; j++) {
          if (unis[j]['name'] == u[i]) {
            v.push(unis[j]);
          }
        }
      }
      setRankings(v);
      console.log(u);





  </script>
</body>
</html>
