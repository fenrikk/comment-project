const commentInput = document.getElementById('commentInput');
const addCommentBtn = document.getElementById('addCommentBtn');
const commentsContainer = document.getElementById('comments');
const alertElement = document.getElementById('alert');
const logoutBtn = document.getElementById('logoutBtn');

let currentUser = null;

// Check if user is logged in
function checkAuth() {
    const token = localStorage.getItem('token');
    if (!token) {
        window.location.href = 'auth.html';
    } else {
        // Here you might want to validate the token with the server
        // For now, we'll just parse the username from the token
        const payload = JSON.parse(atob(token.split('.')[1]));
        currentUser = payload.sub; // Assuming the username is stored in the 'sub' claim
    }
}

checkAuth();

logoutBtn.addEventListener('click', function () {
    localStorage.removeItem('token');
    window.location.href = 'auth.html';
});

addCommentBtn.addEventListener('click', function () {
    onAddCommentClicked();
});

commentInput.addEventListener('keypress', function (event) {
    if (event.key === 'Enter') {
        onAddCommentClicked();
    }
});

fetch('/feed', {
    headers: {
        'Authorization': 'Bearer ' + localStorage.getItem('token')
    }
})
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

    const commentAuthor = document.createElement('div');
    commentAuthor.classList.add('comment-author');
    commentAuthor.textContent = comment.author;

    const commentActions = document.createElement('div');
    commentActions.classList.add('comment-actions');

    if (comment.author === currentUser) {
        const editButton = document.createElement('button');
        editButton.textContent = 'Edit';
        editButton.classList.add('edit');
        editButton.addEventListener('click', () => onEditCommentClicked(comment.id));

        const deleteButton = document.createElement('button');
        deleteButton.textContent = 'Delete';
        deleteButton.addEventListener('click', () => onDeleteCommentClicked(comment.id));

        commentActions.appendChild(editButton);
        commentActions.appendChild(deleteButton);
    }

    commentElement.appendChild(commentText);
    commentElement.appendChild(commentTime);
    commentElement.appendChild(commentAuthor);
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
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.getItem('token')
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
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('token')
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
        method: 'DELETE',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
    })
        .then(() => {
            const commentElement = document.querySelector(`.comment[data-id='${id}']`);
            commentElement.remove();
        })
        .catch(error => {
            console.error('Error:', error);
        });
}