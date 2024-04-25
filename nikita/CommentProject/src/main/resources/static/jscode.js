const commentInput = document.getElementById('commentInput');
const addCommentBtn = document.getElementById('addCommentBtn');
const commentsContainer = document.getElementById('comments');

addCommentBtn.addEventListener('click', function () {
    onAddCommentClicked()
});

fetch('/getComments')
    .then(response => response.json())
    .then(response => {
        response.comments.forEach(
            function (item) {
                addComment(item.comment)
            }
        )
        console.log(response)
    })
    .catch(error => {
        console.error('Помилка:', error);
    })

function addComment(text) {
    const commentElement = document.createElement('div');
    commentElement.classList.add('comment');
    commentElement.textContent = text;
    commentsContainer.prepend(commentElement);
}

function onAddCommentClicked() {
    const commentText = commentInput.value.trim();
    if (commentText !== '') {
        commentInput.value = '';

        fetch('/addComment', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({comment: commentText})
        })
            .then(response => response.json())
            .then(response => addComment(response.comment))
            .catch(error => {
                console.error('Помилка:', error);
            });

    }
}

commentInput.addEventListener('keypress', function (event) {
    if (event.key === 'Enter') {
        onAddCommentClicked()
    }
})