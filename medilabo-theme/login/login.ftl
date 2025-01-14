<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login to Medilabo</title>
    <link rel="stylesheet" href="${url.resourcesPath}/styles.css">
</head>
<body>
<div class="login-container">
    <div class="login-header">
        <h1>Welcome to Medilabo</h1>
        <p>Please enter your credentials to log in.</p>
    </div>
    <form action="${url.loginAction}" method="post" class="login-form">
        <input type="hidden" name="client_id" value="${client.clientId!''}">
        <input type="hidden" name="tab_id" value="${tabId!''}">
        <input type="hidden" name="execution" value="${execution!''}">

        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" id="username" name="username" placeholder="Enter your username" required>
        </div>

        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" placeholder="Enter your password" required>
        </div>

        <button type="submit" class="btn">Log In</button>
    </form>
</div>
</body>
</html>
