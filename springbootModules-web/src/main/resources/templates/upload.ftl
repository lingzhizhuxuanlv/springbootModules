<html>
<head>
    <link rel="shortcut icon" href="favicon.ico" />
    <meta content="text/html;charset=UTF-8">
    <title>文件上传</title>
</head>
<body>
<h2>文件上传</h2>
<form action="${base}/web/upload" method="post" enctype="multipart/form-data">
    <p>选择文件:<input type="file" name="files" multiple></p>
    <p><input type="submit" value="提交"></p>
</form>
</body>
</html>
