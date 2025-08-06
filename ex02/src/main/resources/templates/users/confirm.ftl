<#assign pageTitle = "Confirm Account">
<#assign currentPage = "confirm">

<#include "../layout/base.ftl">

<@base>
    <!-- Page Header -->
    <div class="text-center mb-4">
        <h2><i class="fas fa-user-check me-2"></i>Confirm Your Account</h2>
        <p class="text-muted">Please confirm your account details to continue</p>
    </div>

    <!-- Confirmation Form -->
    <div class="row justify-content-center">
        <div class="col-lg-5 col-md-7">
            <div class="card shadow-sm">
                <div class="card-header bg-primary text-white text-center">
                    <h5 class="card-title mb-0">
                        <i class="fas fa-user-check me-2"></i>Confirm Account
                    </h5>
                </div>
                <div class="card-body">
                    <form id="confirmForm" novalidate>
                        <!-- Email Address -->
                        <div class="mb-3">
                            <label for="email" class="form-label">
                                <i class="fas fa-envelope me-1"></i>Email Address <span class="text-danger">*</span>
                            </label>
                            <input type="email"
                                   class="form-control"
                                   id="email"
                                   name="email"
                                   required
                                   placeholder="Enter your email address">
                            <div class="invalid-feedback">
                                Valid email address is required
                            </div>
                        </div>

                        <!-- Password -->
                        <div class="mb-3">
                            <label for="password" class="form-label">
                                <i class="fas fa-lock me-1"></i>Password <span class="text-danger">*</span>
                            </label>
                            <input type="password"
                                   class="form-control"
                                   id="password"
                                   name="password"
                                   required
                                   minlength="6"
                                   placeholder="Enter your password">
                            <div class="invalid-feedback">
                                Password is required (minimum 6 characters)
                            </div>
                        </div>

                        <!-- Form Actions -->
                        <hr class="my-4">

                        <!-- Display errors if any -->
                        <#if error??>
                            <div class="alert alert-danger text-center mb-3">
                                <i class="fas fa-exclamation-triangle me-2"></i>${error}
                            </div>
                        </#if>

                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-primary">
                                <i class="fas fa-check-circle me-2"></i>Confirm Account
                            </button>
                            <a href="/" class="btn btn-outline-secondary">
                                <i class="fas fa-arrow-left me-2"></i>Cancel
                            </a>
                        </div>
                    </form>
                </div>
                <div class="card-footer text-center text-muted">
                    <small>
                        <i class="fas fa-question-circle me-1"></i>
                        Need help?
                        <a href="/contact" class="text-decoration-none">Contact us</a>
                    </small>
                </div>
            </div>
        </div>
    </div>

    <!-- JavaScript for form submission -->
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const form = document.getElementById('confirmForm');
            const urlPath = window.location.pathname;
            const confirmationCode = urlPath.split('/').pop(); // Get the confirmation code from the URL

            // Form validation
            form.addEventListener('submit', function(event) {
                event.preventDefault();

                if (!form.checkValidity()) {
                    event.stopPropagation();
                    form.classList.add('was-validated');
                    return;
                }

                // Get form data
                const email = document.getElementById('email').value;
                const password = document.getElementById('password').value;

                // Create request data
                const formData = new FormData();
                formData.append('email', email);
                formData.append('password', password);

                // Log the request attempt (for debugging)
                console.log(`Sending confirmation request for code: ${confirmationCode}`);

                // Send PATCH request
                fetch(`/confirm/${confirmationCode}`, {
                    method: 'PATCH',
                    body: formData,
                    credentials: 'same-origin'
                })
                .then(response => {
                    console.log('Response status:', response.status);
                    return response.text().then(text => {
                        console.log('Response text:', text);
                        if (!response.ok) {
                            throw new Error(text || 'Confirmation failed');
                        }
                        return text;
                    });
                })
                .then(data => {
                    // Show success message and redirect
                    const successAlert = document.createElement('div');
                    successAlert.className = 'alert alert-success text-center mb-3';
                    successAlert.innerHTML = '<i class="fas fa-check-circle me-2"></i>Account confirmed successfully! Redirecting...';
                    form.prepend(successAlert);

                    // Redirect to login or dashboard after 2 seconds
                    setTimeout(() => {
                        window.location.href = '/signin';
                    }, 2000);
                })
                .catch(error => {
                    console.error('Error during confirmation:', error);
                    // Show error message
                    const errorAlert = document.createElement('div');
                    errorAlert.className = 'alert alert-danger text-center mb-3';
                    errorAlert.innerHTML = '<i class="fas fa-exclamation-triangle me-2"></i>Failed to confirm account. Please try again.';
                    form.prepend(errorAlert);
                });
            });
        });
    </script>
</@base>
