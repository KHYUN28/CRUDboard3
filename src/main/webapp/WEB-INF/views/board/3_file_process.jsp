<%@ page import="org.apache.commons.fileupload.DiskFileUpload" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="org.apache.commons.fileupload.FileItem" %>
<%@ page import="java.io.File" %>
<body>
<p> Commons-fileupload를 이용하여 파일 업로드 </p>
<%
  String fileUploadPath = "c:\\upload";
  DiskFileUpload upload = new DiskFileUpload(); // 객체화
  List items = upload.parseRequest(request);
  Iterator it = items.iterator();
  while(it.hasNext()){
    FileItem fileItem = (FileItem) it.next();
    if( !fileItem.isFormField()) {
      String fileName = fileItem.getName();
      fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
      File file = new File(fileUploadPath + "/" + fileName);
      fileItem.write(file);
    }
  }
%>
</body>