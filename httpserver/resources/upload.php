<?PHP
  if(!empty($_FILES['uploaded_file']))
  {
    $dirpath = "";
    $path = $dirpath . basename( $_FILES['uploaded_file']['name']);
    $filename = $_FILES['uploaded_file']['name'];
    $filepath = '/usr/local/apache2/htdocs/'.$path;

    if (file_exists($filepath)) {
      echo "The file $filename exists" .PHP_EOL;
    } else if(move_uploaded_file($_FILES['uploaded_file']['tmp_name'], $path)) {
      echo "The file ".  basename( $_FILES['uploaded_file']['name']). 
      " has been uploaded" .PHP_EOL;
    } else{
        echo "There was an error uploading the file, please try again!" .PHP_EOL;
    }
  }
?>
