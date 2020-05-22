function renderQuestionAndAnswersBody()
{
    parseQuestionBodyHTML();
    parseAnswersBodyHTML();
	highlightQuestionCode();
	highlightAllAnswersCode();
}

function parseQuestionBodyHTML()
{
	let elem = document.getElementById("questionBody");
	let elemInner = elem.innerHTML;
	console.log(elemInner);

	let convertedInner = elemInner;
	convertedInner = replaceAll(convertedInner, '&amp;', '&');
	convertedInner = replaceAll(convertedInner, '&lt;', '<');
	convertedInner = replaceAll(convertedInner, '&gt;', '>');
	convertedInner = replaceAll(convertedInner, '&amp;', '&');
	console.log(convertedInner);

    elem.innerHTML = convertedInner;
}

function parseAnswersBodyHTML()
{
	let elems = document.getElementsByClassName("answerBody");

	for (let i = 0; i < elems.length; ++i) {
	    let elem = elems[i];
        let elemInner = elem.innerHTML;
        console.log(elemInner);

        let convertedInner = elemInner;
        convertedInner = replaceAll(convertedInner, '&amp;', '&');
        convertedInner = replaceAll(convertedInner, '&lt;', '<');
        convertedInner = replaceAll(convertedInner, '&gt;', '>');
        convertedInner = replaceAll(convertedInner, '&amp;', '&');
        console.log(convertedInner);

        elem.innerHTML = convertedInner;
    }
}

function replaceAll(string, search, replace) {
  return string.split(search).join(replace);
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
