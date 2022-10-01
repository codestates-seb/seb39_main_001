import React, { useEffect, useRef } from 'react';
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

	useEffect(() => {
		editorRef.current?.getInstance().setMarkdown(formData.content);
	}, [formData.content]);

	const onChange = () => {
		const data = editorRef.current?.getInstance().getMarkdown();
		setFormData({
			...formData,
			content: data,
		});
	};

	return (
		<TextEditorWrapper>
			<Editor
				initialValue={formData.content}
				previewStyle='tab'
				height='400px'
				initialEditType='markdown'
				useCommandShortcut={false}
				hideModeSwitch={true}
				ref={editorRef}
				onChange={onChange}
				toolbarItems={[
					['bold', 'italic'],
					['link', 'quote', 'image'],
					['ol', 'ul', 'indent', 'outdent'],
				]}
				language='ko-KR'
			/>
		</TextEditorWrapper>
	);
};

export default TextEditor;
