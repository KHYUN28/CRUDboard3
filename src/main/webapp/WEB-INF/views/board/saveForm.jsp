<%@ page language="java" contentType="text/html; charset=UTF-16"
    pageEncoding="UTF-16"%>

<%@ include file="../layout/header.jsp" %>

<div class="container">
  <form action="UploadProcess.jsp" method="post" name="fileForm"
        enctype="multipart/form-data" onsubmit="return validationForm(this)">
    <div class="form-group">
      <input type="text" class="form-control" placeholder="Enter Title" id="title">
    </div>

    <div class="form-group">
      <textarea class="form-control summernote" rows="5" id="content"></textarea>
    </div>

    첨부파일:<input type="file" name="attachedFile"/><br/>
    <input type="submit" value="전송하기"/>
  </form>

  <button id="btn-save" class="btn btn-primary">글쓰기 완료</button>
</div>

<style>
    #btn-save { float: right; }
</style>

<script>
  $('.summernote').summernote({
    tabsize: 2,
    height: 300,
    toolbar: [
      ['style', ['style']],
      ['font', ['bold', 'underline', 'clear']],
      ['color', ['color']],
      ['para', ['ul', 'ol', 'paragraph']],
      ['table', ['table']],
      ['insert', ['link', 'picture', 'video']],
      ['view', ['fullscreen', 'codeview', 'help']]
    ]
  });
</script>

<script src="/js/board.js"></script>
