<#assign pageTitle = "Sign In">
<#assign currentPage = "signin">

<#include "../layout/base.ftl">

<@base>
    <!-- Page Header -->
    <div class="text-center mb-4">
        <h2><i class="fas fa-sign-in-alt me-2"></i>Welcome Back</h2>
        <p class="text-muted">Sign in to your account to continue</p>
    </div>

    <!-- Signin Form -->
    <div class="row justify-content-center">
        <div class="col-lg-5 col-md-7">
            <div class="card shadow-sm">
                <div class="card-header bg-primary text-white text-center">
                    <h5 class="card-title mb-0">
                        <i class="fas fa-sign-in-alt me-2"></i>Sign In
                    </h5>
                </div>
                <div class="card-body">
                    <form method="post" action="/login" id="signinForm" novalidate>
                        <!-- CSRF Token for security -->
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

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
                                Please provide a valid email address.
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
                                Please enter your password.
                            </div>
                            <div class="d-flex justify-content-end mt-1">
                                <a href="/forgot-password" class="text-decoration-none small">
                                    <i class="fas fa-question-circle me-1"></i>Forgot Password?
                                </a>
                            </div>
                        </div>

                        <!-- Remember Me -->
                        <div class="mb-3 form-check">
                            <input type="checkbox" class="form-check-input" id="rememberMe" name="rememberMe">
                            <label class="form-check-label" for="rememberMe">
                                <i class="fas fa-cookie me-1"></i>Remember me
                            </label>
                        </div>

                        <!-- Form Actions -->
                        <hr class="my-4">

                        <!-- Handle Spring Security error parameter -->
                        <#if RequestParameters.error??>
                            <#if RequestParameters.error == "auth">
                                <div class="alert alert-warning text-center mb-3">
                                    <i class="fas fa-lock me-2"></i>Please sign in to access this page.
                                </div>
                            <#else>
                                <div class="alert alert-danger text-center mb-3">
                                    <i class="fas fa-exclamation-triangle me-2"></i>Invalid email or password. Please try again.
                                </div>
                            </#if>
                        </#if>

                        <!-- Handle Spring Security success parameter -->
                        <#if RequestParameters.success??>
                            <div class="alert alert-success text-center mb-3">
                                <i class="fas fa-check-circle me-2"></i>Login successful! Welcome back.
                            </div>
                        </#if>

                        <!-- Handle logout parameter -->
                        <#if RequestParameters.logout??>
                            <div class="alert alert-info text-center mb-3">
                                <i class="fas fa-sign-out-alt me-2"></i>You have been logged out successfully.
                            </div>
                        </#if>

                        <!-- Keep existing error handling for controller errors -->
                        <#if error??>
                            <div class="alert alert-danger text-center mb-3">
                                ${error}
                            </div>
                        </#if>

                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary btn-lg">
                                <i class="fas fa-sign-in-alt me-2"></i>Sign In
                            </button>
                        </div>

                        <div class="text-center mt-3">
                            <p class="text-muted mb-0">
                                Don't have an account?
                                <a href="/signup" class="text-decoration-none">Create one here</a>
                            </p>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <#assign additionalJS>
        <script>
            // Form validation
            (function() {
                'use strict';

                const form = document.getElementById('signinForm');

                form.addEventListener('submit', function(event) {
                    if (!form.checkValidity()) {
                        event.preventDefault();
                        event.stopPropagation();
                    }

                    form.classList.add('was-validated');
                }, false);
            })();
        </script>
    </#assign>

    <#assign additionalCSS>
        <style>
            .card {
                border: none;
                border-radius: 0.75rem;
            }
            .card-header {
                border-radius: 0.75rem 0.75rem 0 0 !important;
                border-bottom: none;
            }
            .form-control:focus, .form-select:focus {
                border-color: #86b7fe;
                box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.25);
            }
            .invalid-feedback {
                display: block;
            }
            .was-validated .form-control:invalid,
            .was-validated .form-select:invalid {
                border-color: #dc3545;
                background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 12 12' width='12' height='12' fill='none' stroke='%23dc3545'%3e%3ccircle cx='6' cy='6' r='4.5'/%3e%3cpath d='m5.8 3.6h.4L6 6.5z'/%3e%3ccircle cx='6' cy='8.2' r='.6' fill='%23dc3545' stroke='none'/%3e%3c/svg%3e");
                background-repeat: no-repeat;
                background-position: right calc(0.375em + 0.1875rem) center;
                background-size: calc(0.75em + 0.375rem) calc(0.75em + 0.375rem);
            }
            .was-validated .form-control:valid,
            .was-validated .form-select:valid {
                border-color: #198754;
                background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 8 8'%3e%3cpath fill='%23198754' d='m2.3 6.73.94-.94 1.44 1.44L7.4 4.5 6.46 3.56 4.23 5.79z'/%3e%3c/svg%3e");
                background-repeat: no-repeat;
                background-position: right calc(0.375em + 0.1875rem) center;
                background-size: calc(0.75em + 0.375rem) calc(0.75em + 0.375rem);
            }
        </style>
    </#assign>
</@base>
