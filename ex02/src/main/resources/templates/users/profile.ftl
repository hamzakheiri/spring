<#assign pageTitle = springMacroRequestContext.getMessage("profile.title", [])>
<#assign currentPage = "profile">

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

    <!-- Profile Header -->
    <div class="row mb-4">
        <div class="col-12">
            <div class="card bg-primary text-white">
                <div class="card-body d-flex align-items-center p-4">
                    <div class="me-4">
                        <#if user.avatar?? && user.avatar?has_content>
                            <img src="${user.avatar}" alt="Profile Image" class="rounded-circle" width="100" height="100">
                        <#else>
                            <div class="rounded-circle bg-light text-primary d-flex align-items-center justify-content-center" style="width: 100px; height: 100px; font-size: 2.5rem;">
                                ${user.firstName?substring(0,1)}${user.lastName?substring(0,1)}
                            </div>
                        </#if>
                    </div>
                    <div>
                        <h2 class="mb-1">${user.firstName} ${user.lastName}</h2>
                        <p class="mb-0"><i class="fas fa-user-tag me-2"></i>${user.role}</p>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Profile Details -->
    <div class="row">
        <div class="col-md-8">
            <div class="card mb-4">
                <div class="card-header bg-light">
                    <h5 class="card-title mb-0">
                        <i class="fas fa-id-card me-2"></i>${springMacroRequestContext.getMessage("profile.personalInfo", [])}
                    </h5>
                </div>
                <div class="card-body">
                    <div class="row mb-3">
                        <div class="col-md-3 fw-bold">
                            <i class="fas fa-user me-2"></i>${springMacroRequestContext.getMessage("profile.fullName", [])}:
                        </div>
                        <div class="col-md-9">
                            ${user.firstName} ${user.lastName}
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-md-3 fw-bold">
                            <i class="fas fa-envelope me-2"></i>${springMacroRequestContext.getMessage("profile.email", [])}:
                        </div>
                        <div class="col-md-9">
                            ${user.email}
                        </div>
                    </div>
                    <div class="row mb-3">
                        <div class="col-md-3 fw-bold">
                            <i class="fas fa-phone me-2"></i>${springMacroRequestContext.getMessage("profile.phone", [])}:
                        </div>
                        <div class="col-md-9">
                            ${user.phoneNumber}
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-3 fw-bold">
                            <i class="fas fa-user-shield me-2"></i>${springMacroRequestContext.getMessage("profile.accountStatus", [])}:
                        </div>
                        <div class="col-md-9">
                            <#if user.confirmation == "CONFIRMED">
                                <span class="badge bg-success"><i class="fas fa-check-circle me-1"></i>${springMacroRequestContext.getMessage("profile.verified", [])}</span>
                            <#else>
                                <span class="badge bg-warning"><i class="fas fa-exclamation-circle me-1"></i>${springMacroRequestContext.getMessage("profile.pendingVerification", [])}</span>
                            </#if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <#assign additionalJS>
        <script>
            // Add any JavaScript for the profile page here
            console.log("Profile page loaded");
        </script>
    </#assign>

    <#assign additionalCSS>
        <style>
            .card {
                margin-bottom: 1.5rem;
                border-radius: 0.75rem;
                border: none;
            }

            .card-header {
                border-radius: 0.75rem 0.75rem 0 0 !important;
                background-color: #f8f9fa;
                border-bottom: 1px solid rgba(0, 0, 0, 0.05);
            }

            .btn {
                border-radius: 0.5rem;
                padding: 0.5rem 1rem;
            }
        </style>
    </#assign>
</@base>
