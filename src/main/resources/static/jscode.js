const commentInput = document.getElementById('commentInput');
const addCommentBtn = document.getElementById('addCommentBtn');
const commentsContainer = document.getElementById('comments');
const alertElement = document.getElementById('alert');

addCommentBtn.addEventListener('click', function () {
    onAddCommentClicked();
});

commentInput.addEventListener('keypress', function (event) {
    if (event.key === 'Enter') {
        onAddCommentClicked();
    }
});

fetch('/feed')
    .then(response => response.json())
    .then(comments => {
        comments.forEach(comment => addComment(comment));
    })
    .catch(error => {
        console.error('Error:', error);
    });

function formatTime(timestamp) {
    const date = new Date(timestamp);
    return date.toLocaleString();
}

function addComment(comment) {
    const commentElement = document.createElement('div');
    commentElement.classList.add('comment');
    commentElement.setAttribute('data-id', comment.id);

    const commentText = document.createElement('div');
    commentText.classList.add('comment-text');
    commentText.textContent = comment.message.messageString + (comment.edited ? ' (edited)' : '');

    const commentTime = document.createElement('div');
    commentTime.classList.add('comment-time');
    commentTime.textContent = formatTime(comment.postTime);

    const commentActions = document.createElement('div');
    commentActions.classList.add('comment-actions');

    const editButton = document.createElement('button');
    editButton.textContent = 'Edit';
    editButton.classList.add('edit');
    editButton.addEventListener('click', () => onEditCommentClicked(comment.id));

    const deleteButton = document.createElement('button');
    deleteButton.textContent = 'Delete';
    deleteButton.addEventListener('click', () => onDeleteCommentClicked(comment.id));

    commentActions.appendChild(editButton);
    commentActions.appendChild(deleteButton);

    commentElement.appendChild(commentText);
    commentElement.appendChild(commentTime);
    commentElement.appendChild(commentActions);

    commentsContainer.appendChild(commentElement);
}

function onAddCommentClicked() {
    const commentText = commentInput.value.trim();
    if (commentText.length > 5000) {
        alertElement.style.display = 'block';
        commentInput.value = '';
    } else {
        alertElement.style.display = 'none';
        if (commentText !== '') {
            commentInput.value = '';

            fetch('/comment', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({messageString: commentText})
            })
                .then(response => response.json())
                .then(comment => addComment(comment))
                .catch(error => {
                    console.error('Error:', error);
                });
        }
    }
}

function onEditCommentClicked(id) {
    const newMessage = prompt('Edit your comment:');
    if (newMessage !== null && newMessage.trim() !== '') {
        fetch(`/comment/${id}`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({messageString: newMessage.trim()})
        })
            .then(response => response.json())
            .then(updatedComment => {
                const commentElement = document.querySelector(`.comment[data-id='${id}']`);
                const commentText = commentElement.querySelector('.comment-text');
                const commentTime = commentElement.querySelector('.comment-time');

                commentText.textContent = updatedComment.message.messageString + (updatedComment.edited ? ' (edited)' : '');
                commentTime.textContent = formatTime(updatedComment.postTime);
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }
}

function onDeleteCommentClicked(id) {
    fetch(`/comment/${id}`, {
        method: 'DELETE'
    })
        .then(() => {
            const commentElement = document.querySelector(`.comment[data-id='${id}']`);
            commentElement.remove();
        })
        .catch(error => {
            console.error('Error:', error);
        });
}
