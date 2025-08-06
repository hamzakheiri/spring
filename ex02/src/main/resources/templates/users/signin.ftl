<#assign pageTitle = springMacroRequestContext.getMessage("signin.title", [])>
<#assign currentPage = "signin">

<#include "../layout/base.ftl">

<@base>
    <!-- Language Switcher -->
    <div class="language-switcher text-end mb-3">
        <span><i class="fas fa-globe me-1"></i>${springMacroRequestContext.getMessage("signup.language", [])}: </span>
        <a href="?lang=en" class="me-2 ${(springMacroRequestContext.locale == 'en')?string('fw-bold text-primary', '')}">
            ${springMacroRequestContext.getMessage("signup.english", [])}
        </a> |
        <a href="?lang=fr" class="${(springMacroRequestContext.locale == 'fr')?string('fw-bold text-primary', '')}">
            ${springMacroRequestContext.getMessage("signup.french", [])}
        </a>
    </div>

    <!-- Page Header -->
    <div class="text-center mb-4">
        <h2><i class="fas fa-sign-in-alt me-2"></i>${springMacroRequestContext.getMessage("signin.welcome", [])}</h2>
        <p class="text-muted">${springMacroRequestContext.getMessage("signin.welcomeMessage", [])}</p>
    </div>

    <!-- Signin Form -->
    <div class="row justify-content-center">
        <div class="col-lg-5 col-md-7">
            <div class="card shadow-sm">
                <div class="card-header bg-primary text-white text-center">
                    <h5 class="card-title mb-0">
                        <i class="fas fa-sign-in-alt me-2"></i>${springMacroRequestContext.getMessage("signin.title", [])}
                    </h5>
                </div>
                <div class="card-body">
                    <form method="post" action="/login" id="signinForm" novalidate>
                        <!-- CSRF Token for security -->
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                        <!-- Email Address -->
                        <div class="mb-3">
                            <label for="email" class="form-label">
                                <i class="fas fa-envelope me-1"></i>${springMacroRequestContext.getMessage("signin.email", [])} <span class="text-danger">*</span>
                            </label>
                            <input type="email"
                                   class="form-control"
                                   id="email"
                                   name="email"
                                   required
                                   placeholder="${springMacroRequestContext.getMessage("signin.email.placeholder", [])}">
                            <div class="invalid-feedback">
                                ${springMacroRequestContext.getMessage("signup.email.error", [])}
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="password" class="form-label">
                                <i class="fas fa-lock me-1"></i>${springMacroRequestContext.getMessage("signin.password", [])} <span class="text-danger">*</span>
                            </label>
                            <input type="password"
                                   class="form-control"
                                   id="password"
                                   name="password"
                                   required
                                   minlength="6"
                                   placeholder="${springMacroRequestContext.getMessage("signin.password.placeholder", [])}">
                            <div class="invalid-feedback">
                                ${springMacroRequestContext.getMessage("signup.password.error", [])}
                            </div>
                        </div>

                        <!-- Remember Me -->
                        <div class="mb-3 form-check">
                            <input type="checkbox" class="form-check-input" id="rememberMe" name="rememberMe">
                            <label class="form-check-label" for="rememberMe">
                                <i class="fas fa-cookie me-1"></i>${springMacroRequestContext.getMessage("signin.rememberMe", [])}
                            </label>
                        </div>

                        <!-- Form Actions -->
                        <hr class="my-4">

                        <!-- Handle Spring Security error parameter -->
                        <#if RequestParameters.error??>
                            <#if RequestParameters.error == "auth">
                                <div class="alert alert-warning text-center mb-3">
                                    <i class="fas fa-lock me-2"></i>${springMacroRequestContext.getMessage("signin.error.auth", [])}
                                </div>
                            <#else>
                                <div class="alert alert-danger text-center mb-3">
                                    <i class="fas fa-exclamation-triangle me-2"></i>${springMacroRequestContext.getMessage("signin.error.credentials", [])}
                                </div>
                            </#if>
                        </#if>

                        <!-- Handle unconfirmed account parameter -->
                        <#if RequestParameters.unconfirmed??>
                            <div class="alert alert-warning text-center mb-3">
                                <i class="fas fa-envelope me-2"></i>
                                Your account has not been confirmed yet. Please check your email and click the confirmation link.
                            </div>
                        </#if>

                        <#if RequestParameters.success?? || success??>
                            <div class="alert alert-success text-center mb-3">
                                <i class="fas fa-check-circle me-2"></i>
                                <#if success??>
                                    ${success}
                                <#else>
                                    ${springMacroRequestContext.getMessage('signin.success', [])}
                                </#if>
                            </div>
                        </#if>

                        <!-- Handle logout parameter -->
                        <#if RequestParameters.logout??>
                            <div class="alert alert-info text-center mb-3">
                                <i class="fas fa-sign-out-alt me-2"></i>${springMacroRequestContext.getMessage('signin.logout', [])}
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
                                <i class="fas fa-sign-in-alt me-2"></i>${springMacroRequestContext.getMessage('signin.button', [])}
                            </button>
                        </div>

                        <div class="text-center mt-3">
                            <p class="text-muted mb-0">
                                ${springMacroRequestContext.getMessage('signin.noAccount', [])}
                                <a href="/signup" class="text-decoration-none">${springMacroRequestContext.getMessage('signin.createAccount', [])}</a>
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
