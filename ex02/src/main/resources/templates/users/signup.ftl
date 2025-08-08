<#assign pageTitle = "${springMacroRequestContext.getMessage('signup.title', [])}">
<#assign currentPage = "signup">

<#include "../layout/base.ftl">

<@base>
    <!-- Language Switcher -->
    <div class="language-switcher text-end mb-3">
        <span><i class="fas fa-globe me-1"></i>${springMacroRequestContext.getMessage('signup.language', [])}: </span>
        <a href="?lang=en" class="me-2 ${(springMacroRequestContext.locale == 'en')?string('fw-bold text-primary', '')}">
            ${springMacroRequestContext.getMessage('signup.english', [])}
        </a> |
        <a href="?lang=fr" class="${(springMacroRequestContext.locale == 'fr')?string('fw-bold text-primary', '')}">
            ${springMacroRequestContext.getMessage('signup.french', [])}
        </a>
    </div>

    <!-- Page Header -->
    <div class="text-center mb-4">
        <h2><i class="fas fa-user-plus me-2"></i>${springMacroRequestContext.getMessage('signup.title', [])}</h2>
        <#if success??>
            <p class="text-success">${springMacroRequestContext.getMessage('signup.success', [])}</p>
        </#if>
    </div>

    <!-- Signup Form -->
    <div class="row justify-content-center">
        <div class="col-lg-6 col-md-8">
            <div class="card shadow-sm">
                <div class="card-header bg-primary text-white text-center">
                    <h5 class="card-title mb-0">
                        <i class="fas fa-user-plus me-2"></i>${springMacroRequestContext.getMessage('signup.title', [])}
                    </h5>
                </div>
                <div class="card-body">
                    <form method="post" action="/signup" id="signupForm" enctype="multipart/form-data" novalidate>
                        <!-- CSRF Token for security -->
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                        <!-- Personal Information -->
                        <div class="mb-3">
                            <label for="firstName" class="form-label">
                                <i class="fas fa-user me-1"></i>${springMacroRequestContext.getMessage('signup.firstName', [])} <span class="text-danger">*</span>
                            </label>
                            <input type="text"
                                   class="form-control <#if (bindingResult?? && bindingResult.hasFieldErrors('firstName'))>is-invalid</#if>"
                                   id="firstName"
                                   name="firstName"
                                   value="<#if user??>${user.firstName!''}</#if>"
                                   required
                                   minlength="2"
                                   maxlength="50"
                                   placeholder="${springMacroRequestContext.getMessage('signup.firstName.placeholder', [])}">
                            <#if bindingResult?? && bindingResult.hasFieldErrors('firstName')>
                                <div class="invalid-feedback">
                                    ${bindingResult.getFieldError('firstName').getDefaultMessage()!''}
                                </div>
                            <#else>
                                <div class="invalid-feedback">
                                    ${springMacroRequestContext.getMessage('signup.firstName.error', [])}
                                </div>
                            </#if>
                            <div class="form-text">
                                <i class="fas fa-info-circle me-1"></i>First name cannot be empty
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="lastName" class="form-label">
                                <i class="fas fa-user me-1"></i>${springMacroRequestContext.getMessage('signup.lastName', [])} <span class="text-danger">*</span>
                            </label>
                            <input type="text"
                                   class="form-control <#if (bindingResult?? && bindingResult.hasFieldErrors('lastName'))>is-invalid</#if>"
                                   id="lastName"
                                   name="lastName"
                                   value="<#if user??>${user.lastName!''}</#if>"
                                   required
                                   minlength="2"
                                   maxlength="50"
                                   placeholder="${springMacroRequestContext.getMessage('signup.lastName.placeholder', [])}">
                            <#if bindingResult?? && bindingResult.hasFieldErrors('lastName')>
                                <div class="invalid-feedback">
                                    ${bindingResult.getFieldError('lastName').getDefaultMessage()!''}
                                </div>
                            <#else>
                                <div class="invalid-feedback">
                                    ${springMacroRequestContext.getMessage('signup.lastName.error', [])}
                                </div>
                            </#if>
                            <div class="form-text">
                                <i class="fas fa-info-circle me-1"></i>Last name cannot be empty
                            </div>
                        </div>

                        <!-- Contact Information -->
                        <div class="mb-3">
                            <label for="email" class="form-label">
                                <i class="fas fa-envelope me-1"></i>${springMacroRequestContext.getMessage('signup.email', [])} <span class="text-danger">*</span>
                            </label>
                            <input type="email"
                                   class="form-control <#if (bindingResult?? && bindingResult.hasFieldErrors('email'))>is-invalid</#if>"
                                   id="email"
                                   name="email"
                                   value="<#if user??>${user.email!''}</#if>"
                                   required
                                   placeholder="${springMacroRequestContext.getMessage('signup.email.placeholder', [])}">
                            <#if bindingResult?? && bindingResult.hasFieldErrors('email')>
                                <div class="invalid-feedback">
                                    ${bindingResult.getFieldError('email').getDefaultMessage()!''}
                                </div>
                            <#else>
                                <div class="invalid-feedback">
                                    ${springMacroRequestContext.getMessage('signup.email.error', [])}
                                </div>
                            </#if>
                            <div class="form-text">
                                <i class="fas fa-info-circle me-1"></i>Email must be a valid email address format
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="phoneNumber" class="form-label">
                                <i class="fas fa-phone me-1"></i>${springMacroRequestContext.getMessage('signup.phoneNumber', [])} <span class="text-danger">*</span>
                            </label>
                            <input type="tel"
                                   class="form-control <#if (bindingResult?? && bindingResult.hasFieldErrors('phoneNumber'))>is-invalid</#if>"
                                   id="phoneNumber"
                                   name="phoneNumber"
                                   value="<#if user??>${user.phoneNumber!''}</#if>"
                                   required
                                   placeholder="${springMacroRequestContext.getMessage('signup.phoneNumber.placeholder', [])}">
                            <#if bindingResult?? && bindingResult.hasFieldErrors('phoneNumber')>
                                <div class="invalid-feedback">
                                    ${bindingResult.getFieldError('phoneNumber').getDefaultMessage()!''}
                                </div>
                            <#else>
                                <div class="invalid-feedback">
                                    ${springMacroRequestContext.getMessage('signup.phoneNumber.error', [])}
                                </div>
                            </#if>
                            <div class="form-text">
                                <i class="fas fa-info-circle me-1"></i>Phone number must match format: +7(777)777777
                            </div>
                        </div>

                        <!-- Password Information -->
                        <div class="mb-3">
                            <label for="password" class="form-label">
                                <i class="fas fa-lock me-1"></i>${springMacroRequestContext.getMessage('signup.password', [])} <span class="text-danger">*</span>
                            </label>
                            <input type="password"
                                   class="form-control <#if (bindingResult?? && bindingResult.hasFieldErrors('password'))>is-invalid</#if>"
                                   id="password"
                                   name="password"
                                   required
                                   minlength="8"
                                   placeholder="${springMacroRequestContext.getMessage('signup.password.placeholder', [])}">
                            <#if bindingResult?? && bindingResult.hasFieldErrors('password')>
                                <div class="invalid-feedback">
                                    ${bindingResult.getFieldError('password').getDefaultMessage()!''}
                                </div>
                            <#else>
                                <div class="invalid-feedback">
                                    ${springMacroRequestContext.getMessage('signup.password.error', [])}
                                </div>
                            </#if>
                            <div class="form-text">
                                <i class="fas fa-info-circle me-1"></i>Password must:
                                <ul class="mb-0">
                                    <li>Be at least 8 characters long</li>
                                    <li>Contain at least one uppercase letter (A-Z)</li>
                                    <li>Contain at least one lowercase letter (a-z)</li>
                                    <li>Contain at least one digit (0-9)</li>
                                </ul>
                            </div>
                        </div>

                        <div class="mb-3">
                            <label for="confirmPassword" class="form-label">
                                <i class="fas fa-lock me-1"></i>${springMacroRequestContext.getMessage('signup.confirmPassword', [])} <span class="text-danger">*</span>
                            </label>
                            <input type="password"
                                   class="form-control <#if passwordMatchError??>is-invalid</#if>"
                                   id="confirmPassword"
                                   name="confirmPassword"
                                   required
                                   minlength="8"
                                   placeholder="${springMacroRequestContext.getMessage('signup.confirmPassword.placeholder', [])}">
                            <#if passwordMatchError??>
                                <div class="invalid-feedback">
                                    ${passwordMatchError}
                                </div>
                            <#else>
                                <div class="invalid-feedback">
                                    ${springMacroRequestContext.getMessage('signup.confirmPassword.error', [])}
                                </div>
                            </#if>
                            <div class="form-text">
                                <i class="fas fa-info-circle me-1"></i>Must match the password field exactly
                            </div>
                        </div>

                        <!-- Optional Avatar Upload -->
                        <div class="mb-3">
                            <label for="avatar" class="form-label">
                                <i class="fas fa-image me-1"></i>${springMacroRequestContext.getMessage('signup.avatar', [])} <span class="text-muted">(${springMacroRequestContext.getMessage('signup.avatar.optional', [])})</span>
                            </label>
                            <div class="avatar-upload-container">
                                <!-- Avatar Preview -->
                                <div class="avatar-preview mb-3">
                                    <div class="avatar-preview-circle" id="avatarPreview">
                                        <i class="fas fa-user fa-3x text-muted"></i>
                                        <img id="avatarImage" src="" alt="Avatar Preview" style="display: none;">
                                    </div>
                                </div>

                                <!-- File Input -->
                                <input type="file"
                                       class="form-control"
                                       id="avatar"
                                       name="avatarFile"
                                       accept="image/*"
                                       onchange="previewAvatar(this)">
                                <div class="form-text">
                                    <i class="fas fa-info-circle me-1"></i>${springMacroRequestContext.getMessage('signup.avatar.info', [])}
                                </div>
                            </div>
                        </div>

                        <!-- Hidden role field - defaults to USER -->
                        <input type="hidden" name="role" value="USER">

                        <!-- Form Actions -->
                        <hr class="my-4">

                        <#if error??>
                            <div class="alert alert-danger text-center mb-3">
                                ${error}
                            </div>
                        </#if>

                        <#if bindingResult?? && bindingResult.hasErrors()>
                            <div class="alert alert-danger mb-3">
                                <h5 class="alert-heading text-center">${springMacroRequestContext.getMessage('signup.error', [])}</h5>
                                <hr>
                                <ul class="mb-0">
                                    <#list bindingResult.fieldErrors as error>
                                        <li><strong>${error.field}:</strong> ${error.defaultMessage!''}</li>
                                    </#list>
                                </ul>
                            </div>
                        </#if>

                        <div class="d-grid">
                            <button type="submit" class="btn btn-primary btn-lg">
                                <i class="fas fa-user-plus me-2"></i>${springMacroRequestContext.getMessage('signup.createAccount', [])}
                            </button>
                        </div>

                        <div class="text-center mt-3">
                            <p class="text-muted mb-0">
                                ${springMacroRequestContext.getMessage('signup.alreadyHaveAccount', [])}
                                <a href="/login" class="text-decoration-none">${springMacroRequestContext.getMessage('signup.signInHere', [])}</a>
                            </p>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <#assign additionalJS>
        <script>
            // Avatar preview functionality
            function previewAvatar(input) {
                const preview = document.getElementById('avatarPreview');
                const image = document.getElementById('avatarImage');
                const defaultIcon = preview.querySelector('i');

                if (input.files && input.files[0]) {
                    const file = input.files[0];

                    // Validate file size (5MB max)
                    if (file.size > 5 * 1024 * 1024) {
                        showError('File size must be less than 5MB');
                        resetAvatarPreview(input, image, defaultIcon, preview);
                        return;
                    }

                    // Validate file type
                    if (!file.type.startsWith('image/')) {
                        showError('Please select a valid image file');
                        resetAvatarPreview(input, image, defaultIcon, preview);
                        return;
                    }

                    // Additional validation for common image types
                    const allowedTypes = ['image/jpeg', 'image/jpg', 'image/png', 'image/gif', 'image/webp'];
                    if (!allowedTypes.includes(file.type)) {
                        showError('Please select a JPEG, PNG, GIF, or WebP image file');
                        resetAvatarPreview(input, image, defaultIcon, preview);
                        return;
                    }

                    const reader = new FileReader();
                    reader.onload = function(e) {
                        image.src = e.target.result;
                        image.style.display = 'block';
                        defaultIcon.style.display = 'none';
                        preview.classList.add('has-image');
                    };
                    reader.onerror = function() {
                        showError('Error reading the selected file');
                        resetAvatarPreview(input, image, defaultIcon, preview);
                    };
                    reader.readAsDataURL(file);
                } else {
                    // Reset to default state
                    resetAvatarPreview(input, image, defaultIcon, preview);
                }
            }

            function resetAvatarPreview(input, image, defaultIcon, preview) {
                input.value = '';
                image.style.display = 'none';
                defaultIcon.style.display = 'block';
                preview.classList.remove('has-image');
            }

            function showError(message) {
                // Create or update error message
                let errorDiv = document.getElementById('avatar-error');
                if (!errorDiv) {
                    errorDiv = document.createElement('div');
                    errorDiv.id = 'avatar-error';
                    errorDiv.className = 'alert alert-danger mt-2';
                    document.querySelector('.avatar-upload-container').appendChild(errorDiv);
                }
                errorDiv.textContent = message;
                errorDiv.style.display = 'block';

                // Hide error after 5 seconds
                setTimeout(() => {
                    errorDiv.style.display = 'none';
                }, 5000);
            }

            // Form validation
            (function() {
                'use strict';

                const form = document.getElementById('signupForm');
                const password = document.getElementById('password');
                const confirmPassword = document.getElementById('confirmPassword');

                // Custom password confirmation validation
                function validatePasswordMatch() {
                    if (password.value !== confirmPassword.value) {
                        confirmPassword.setCustomValidity('Passwords do not match');
                    } else {
                        confirmPassword.setCustomValidity('');
                    }
                }

                password.addEventListener('input', validatePasswordMatch);
                confirmPassword.addEventListener('input', validatePasswordMatch);

                form.addEventListener('submit', function(event) {
                    validatePasswordMatch();

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

            /* Avatar Upload Styles */
            .avatar-upload-container {
                text-align: center;
            }
            .avatar-preview {
                display: flex;
                justify-content: center;
                align-items: center;
            }
            .avatar-preview-circle {
                width: 120px;
                height: 120px;
                border-radius: 50%;
                border: 3px dashed #dee2e6;
                display: flex;
                align-items: center;
                justify-content: center;
                background-color: #f8f9fa;
                transition: all 0.3s ease;
                position: relative;
                overflow: hidden;
            }
            .avatar-preview-circle:hover {
                border-color: #0d6efd;
                background-color: #e7f1ff;
            }
            .avatar-preview-circle.has-image {
                border-style: solid;
                border-color: #0d6efd;
            }
            .avatar-preview-circle img {
                width: 100%;
                height: 100%;
                object-fit: cover;
                border-radius: 50%;
            }
            .avatar-preview-circle i {
                color: #6c757d;
                transition: color 0.3s ease;
            }
            .avatar-preview-circle:hover i {
                color: #0d6efd;
            }

            /* File input styling */
            input[type="file"] {
                cursor: pointer;
            }
            input[type="file"]::-webkit-file-upload-button {
                background-color: #0d6efd;
                color: white;
                border: none;
                padding: 0.375rem 0.75rem;
                border-radius: 0.375rem;
                cursor: pointer;
                margin-right: 0.5rem;
            }
            input[type="file"]::-webkit-file-upload-button:hover {
                background-color: #0b5ed7;
            }
        </style>
    </#assign>
</@base>
