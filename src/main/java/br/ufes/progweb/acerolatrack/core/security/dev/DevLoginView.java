package br.ufes.progweb.acerolatrack.core.security.dev;

import br.ufes.progweb.acerolatrack.core.security.CurrentUser;
import br.ufes.progweb.acerolatrack.core.service.impl.ManageUserService;
import br.ufes.progweb.acerolatrack.model.Worker;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.page.WebStorage;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.spring.security.AuthenticationContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Login view for development.
 */
@PageTitle("Login")
@AnonymousAllowed
// No @Route annotation - the route is registered dynamically by DevSecurityConfig.
class DevLoginView extends Main implements BeforeEnterObserver {

    static final String LOGIN_PATH = "dev-login";
    private static final String CALLOUT_HIDDEN_KEY = "walking-skeleton-dev-login-callout-hidden";

    private CurrentUser currentUser;
    private ManageUserService manageUserService;
    private final AuthenticationContext authenticationContext;
    private final LoginForm login;

    DevLoginView(ManageUserService manageUserService, AuthenticationContext authenticationContext) {
        this.authenticationContext = authenticationContext;
        this.manageUserService = manageUserService;

        // Create the components
        login = new LoginForm();
        login.setAction(LOGIN_PATH);
        login.setForgotPasswordButtonVisible(false);

        var exampleUsers = new Div(new Div("Use the following details to login"));
        SampleUsers.ALL_USERS.forEach(user -> exampleUsers.add(createSampleUserCard(user)));

        // Configure the view
        setSizeFull();
        addClassNames("dev-login-view");

        exampleUsers.addClassNames("dev-users");

        var contentDiv = new Div(login, exampleUsers);
        contentDiv.addClassNames("dev-content-div");
        add(contentDiv);

        var devModeMenuDiv = new Div("You can also use the Dev Mode Menu here to impersonate any user");
        devModeMenuDiv.addClassNames("dev-mode-speech-bubble");
        // Hide the callout when clicked
        devModeMenuDiv.addClickListener(event -> {
            WebStorage.setItem(WebStorage.Storage.LOCAL_STORAGE, CALLOUT_HIDDEN_KEY, "1");
            devModeMenuDiv.setVisible(false);
        });
        devModeMenuDiv.setVisible(false);
        add(devModeMenuDiv);

        // Don't show the callout if already hidden once
        WebStorage.getItem(WebStorage.Storage.LOCAL_STORAGE, CALLOUT_HIDDEN_KEY,
                value -> devModeMenuDiv.setVisible(value == null));
    }

    private Component createSampleUserCard(DevUser user) {
        var card = new Div();
        card.addClassNames("dev-user-card");

        var fullName = new H3(user.getAppUser().getFullName());

        var credentials = new DescriptionList();
        credentials.add(new DescriptionList.Term("Username"), new DescriptionList.Description(user.getUsername()));
        credentials.add(new DescriptionList.Term("Password"),
                new DescriptionList.Description(SampleUsers.SAMPLE_PASSWORD));

        // Make it easier to log in while still going through the normal authentication process.
        var loginButton = new Button(VaadinIcon.SIGN_IN.create(), event -> {
            login.getElement().executeJs("""
                    document.getElementById("vaadinLoginUsername").value = $0;
                    document.getElementById("vaadinLoginPassword").value = $1;
                    document.forms[0].submit();
                    """, user.getUsername(), SampleUsers.SAMPLE_PASSWORD);
        });
        loginButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY);

        card.add(new Div(fullName, credentials));
        card.add(loginButton);

        return card;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (authenticationContext.isAuthenticated()) {

            Pageable pageable = PageRequest.of(0, 10);

            List<Worker> workerList =  manageUserService.getAllWorkers(pageable).getContent().stream().toList();

            // Redirect to the main view if the user is already logged in. This makes impersonation easier to work with.
            event.forwardTo("");
            return;
        }

        if (event.getLocation().getQueryParameters().getParameters().containsKey("error")) {
            login.setError(true);
        }
    }
}
