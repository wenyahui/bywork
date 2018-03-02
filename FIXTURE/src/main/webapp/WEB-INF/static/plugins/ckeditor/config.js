/**
 * @license Copyright (c) 2003-2016, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {

	config.toolbar = 'Full';
		
	config.toolbar_Full =     
	[     
		['Source'],     
		['Cut','Copy','Paste','PasteText','PasteFromWord', 'SpellChecker'],
		['Undo','Redo','RemoveFormat'],  
		['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
		['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'], 
		'/',
		['Link','Unlink','Anchor'],
		['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak','EqnEditor'],
		['Styles','Format','Font','FontSize'],
		['TextColor','BGColor'],
		['Maximize', 'ShowBlocks']
	];     
		
	config.toolbar_Basic =     
	[     
		['Bold', 'Italic', '-', 'NumberedList', 'BulletedList', '-', 'Link', 'Unlink']     
	];
	//config.extraPlugins += (config.extraPlugins ? ",jme" : "jme");
	//config.extraPlugins += (config.extraPlugins ? ",tomjme" : "tomjme");
	//config.allowedContent = true;
	config.removeDialogTabs = 'image:advanced;link:target;'
	config.removeDialogTabs = 'link:advanced;link:target;image:advanced;image:target;';
	config.image_previewText = '';
	config.filebrowserImageUploadUrl = "/upload/CKimage";
};