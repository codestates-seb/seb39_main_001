import React, { useEffect, useRef } from 'react';
import { Editor } from '@toast-ui/react-editor';
import '@toast-ui/editor/dist/toastui-editor.css';
import styled from 'styled-components';

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

const TextEditorWrapper = styled.div`
	display: flex;
	flex-direction: column;
	width: 800px;
	height: 500px;
	.tab-item {
		display: none;
	}
	.toastui-editor-defaultUI-toolbar {
		background-color: ${({ theme }) => theme.background};
	}
	.toastui-editor-toolbar-icons {
		border: none;
	}
	.toastui-editor-defaultUI {
		border: 1px solid ${({ theme }) => theme.colors.grey4};
	}
	.toastui-editor-md-tab-container {
		background: ${({ theme }) => theme.background};
	}
	.ProseMirror {
		color: ${({ theme }) => theme.text};
	}
	.toastui-editor-toolbar-divider {
		background-color: ${({ theme }) => theme.colors.grey1};
	}
`;

export default TextEditor;
