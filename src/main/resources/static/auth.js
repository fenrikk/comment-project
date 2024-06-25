const formTitle = document.getElementById('formTitle');
const submitBtn = document.getElementById('submitBtn');
const switchBtn = document.getElementById('switchBtn');
const switchText = document.getElementById('switchText');
const usernameInput = document.getElementById('username');
const passwordInput = document.getElementById('password');

let isLoginMode = true;

switchBtn.addEventListener('click', function (e) {
    e.preventDefault();
    isLoginMode = !isLoginMode;
    updateFormUI();
});

submitBtn.addEventListener('click', function () {
    const username = usernameInput.value;
    const password = passwordInput.value;

    if (isLoginMode) {
        login(username, password);
    } else {
        register(username, password);
    }
});

function updateFormUI() {
    if (isLoginMode) {
        formTitle.textContent = 'Login';
        submitBtn.textContent = 'Login';
        switchText.innerHTML = 'Don\'t have an account? <a href="#" id="switchBtn">Register</a>';
    } else {
        formTitle.textContent = 'Register';
        submitBtn.textContent = 'Register';
        switchText.innerHTML = 'Already have an account? <a href="#" id="switchBtn">Login</a>';
    }
    // Re-add event listener to new switchBtn
    document.getElementById('switchBtn').addEventListener('click', function (e) {
        e.preventDefault();
        isLoginMode = !isLoginMode;
        updateFormUI();
    });
}

function login(username, password) {
    fetch('/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({username, password})
    })
        .then(response => response.json())
        .then(data => {
            if (data.jwt) {
                localStorage.setItem('token', data.jwt);
                window.location.href = 'index.html';
            } else {
                alert('Login failed');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Login failed');
        });
}

function register(username, password) {
    fetch('/auth/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({username, password})
    })
        .then(response => {
            if (response.ok) {
                alert('Registration successful. Please login.');
                isLoginMode = true;
                updateFormUI();
                usernameInput.value = '';
                passwordInput.value = '';
            } else {
                alert('Registration failed');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Registration failed');
        });
}