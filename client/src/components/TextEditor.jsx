import styled from 'styled-components';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';

const TextEditor = ({ formData, setFormData }) => {
  // react-quill setting
  const toolbarOptions = [
    [{ header: [1, 2, 3, false] }],
    ['bold', 'italic', 'underline', 'strike'],
    ['blockquote', 'code-block'],
    [{ list: 'ordered' }, { list: 'bullet' }],
    [{ color: [] }, { background: [] }],
    [{ align: [] }],
  ];
  const modules = {
    toolbar: {
      container: toolbarOptions,
    },
  };

  // content input handler
  const inputChange = (e, delta, source, editor) => {
    setFormData((prev) => ({ ...prev, content: e }));
    console.log(editor.getContents());
  };

  return (
    <TextEditorWrapper>
      <ReactQuill
        placeholder='본문을 입력해주세요.'
        value={formData.content || ''}
        theme='snow'
        modules={modules}
        onChange={inputChange}
      />
    </TextEditorWrapper>
  );
};

const TextEditorWrapper = styled.div`
  display: flex;
  flex-direction: column;
  width: 800px;
  height: 500px;
  > .quill {
    height: 400px;
    > div {
      border: 1px solid ${({ theme }) => theme.colors.grey3};
      &.ql-toolbar {
        border-radius: 4px 4px 0 0;
      }
      &.ql-container {
        border-radius: 0 0 4px 4px;
        > .ql-editor {
          font-family: pretendard;
          font-size: 16px;
          h1,
          h2,
          h3 {
            font-weight: 600;
          }
          em {
            font-style: italic;
          }
        }
        // placeholder style
        .ql-blank::before {
          color: ${({ theme }) => theme.colors.grey4};
        }
      }
    }
  }
`;

export default TextEditor;
