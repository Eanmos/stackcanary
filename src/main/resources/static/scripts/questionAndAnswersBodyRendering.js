function renderQuestionAndAnswersBody()
{
	convertQuestionMarkdownToHTML();
	convertAllAnswersMarkdownToHTML();

	highlightQuestionCode();
	highlightAllAnswersCode();
}

function convertQuestionMarkdownToHTML()
{
	  let  elem       = document.getElementById("questionBody");
	const  md         = elem.textContent;
	const  html       = markdown.toHTML(md);
	elem.innerHTML    = html;
}

function convertAllAnswersMarkdownToHTML()
{
	let elems = document.getElementsByClassName("answerBody");
	
	for (let i = 0; i < elems.length; ++i) {
		const md             = elems[i].textContent;
		const html           = markdown.toHTML(md);
		elems[i].innerHTML   = html;
	}
}

function highlightQuestionCode()
{
	let  elem       = document.getElementById("questionBody");
	let children    = elem.getElementsByTagName("*");
	
	for (let i = 0; i < children.length; ++i)
		if (children[i].tagName === "CODE" && children[i].parentElement.tagName === "PRE")
			hljs.highlightBlock(children[i]);
}

function highlightAllAnswersCode()
{
	let  elems = document.getElementsByClassName("answerBody");

	for (let i = 0; i < elems.length; ++i) {
		let children = elems[i].getElementsByTagName("*");
		
		for (let j = 0; j < children.length; ++j)
			if (children[j].tagName === "CODE" && children[j].parentElement.tagName === "PRE")
				hljs.highlightBlock(children[j]);
	}
}
