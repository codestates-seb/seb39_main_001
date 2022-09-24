import React, { useRef } from 'react';
import { Editor } from '@toast-ui/react-editor';
import '@toast-ui/editor/dist/toastui-editor.css';
import styled from 'styled-components';

const TextEditorWrapper = styled.div`
  display: flex;
  flex-direction: column;
  width: 800px;
  height: 500px;
  .tab-item {
    display: none;
  }
`;

const TextEditor = ({ formData, setFormData }) => {
  const editorRef = useRef();
  const onChange = () => {
    const data = editorRef.current?.getInstance().getHTML();
    setFormData({
      ...formData,
      content: data,
    });
    console.log(data);
  };

  return (
    <TextEditorWrapper>
      <Editor
        initialValue={formData.content || ' '}
        previewStyle="tab"
        height="400px"
        initialEditType="wysiwyg"
        useCommandShortcut={false}
        hideModeSwitch={true}
        ref={editorRef}
        onChange={onChange}
        toolbarItems={[
          ['bold', 'italic'],
          ['link', 'quote', 'image'],
          ['ol', 'ul', 'indent', 'outdent'],
        ]}
        language="ko-KR"
      />
    </TextEditorWrapper>
  );
};

export default TextEditor;
