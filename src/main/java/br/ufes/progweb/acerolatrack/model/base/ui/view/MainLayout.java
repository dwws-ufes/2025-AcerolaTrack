package br.ufes.progweb.acerolatrack.model.base.ui.view;

import br.ufes.progweb.acerolatrack.core.security.CurrentUser;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.server.menu.MenuEntry;
import com.vaadin.flow.spring.security.AuthenticationContext;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;

import static com.vaadin.flow.theme.lumo.LumoUtility.*;

@Layout
@PermitAll // When security is enabled, allow all authenticated users

public class MainLayout extends AppLayout {

    private final CurrentUser currentUser;
    private final AuthenticationContext authenticationContext;

    public MainLayout(CurrentUser currentUser, AuthenticationContext authenticationContext) {
        DrawerToggle toggle = new DrawerToggle();
        this.currentUser = currentUser;
        this.authenticationContext = authenticationContext;
        setPrimarySection(Section.DRAWER);
        addToDrawer(createHeader(), new Scroller(getSideNav()), createUserMenu());

        H1 title = new H1("acerola Track");
        title.getStyle().set("font-size", "var(--lumo-font-size-l)")
                .set("margin", "0");

//        SideNav nav = getSideNav();
//
//        Scroller scroller = new Scroller(nav);
//        scroller.setClassName(LumoUtility.Padding.SMALL);
//
//        addToDrawer(scroller);
        addToNavbar(toggle, title);
    }

    private Component createUserMenu() {
        var user = currentUser.require();

        var avatar = new Avatar(user.getFullName(), user.getPictureUrl());
        avatar.addThemeVariants(AvatarVariant.LUMO_XSMALL);
        avatar.addClassNames(Margin.Right.SMALL);
        avatar.setColorIndex(5);

        var userMenu = new MenuBar();
        userMenu.addThemeVariants(MenuBarVariant.LUMO_TERTIARY_INLINE);
        userMenu.addClassNames(Margin.MEDIUM);

        var userMenuItem = userMenu.addItem(avatar);
        userMenuItem.add(user.getFullName());
        if (user.getProfileUrl() != null) {
            userMenuItem.getSubMenu().addItem("View Profile",
                    event -> UI.getCurrent().getPage().open(user.getProfileUrl()));
        }
        // TODO Add additional items to the user menu if needed
        userMenuItem.getSubMenu().addItem("Logout", event -> authenticationContext.logout());

        return userMenu;
    }

    private SideNav getSideNav() {
        SideNav sideNav = new SideNav();
        sideNav.addItem(
                new SideNavItem("Dashboard", "/project-reports",
                        VaadinIcon.DASHBOARD.create()),
//                new SideNavItem("Orders", "/orders", VaadinIcon.CART.create()),
                new SideNavItem("Create Task", "/tasks/create",
                        VaadinIcon.TASKS.create()),
                new SideNavItem("Time Entries", "/time-entries",
                        VaadinIcon.LIST_SELECT.create()),
                new SideNavItem("Add Time Entry", "/time-entries/create",
                        VaadinIcon.CLOCK.create()),
                new SideNavItem("Create Project", "projects/create",
                        VaadinIcon.ARCHIVE.create()),
                new SideNavItem("Projects", "/projects",
                        VaadinIcon.PACKAGE.create()),
//                new SideNavItem("Documents", "/documents",
//                        VaadinIcon.RECORDS.create()),
                new SideNavItem("Tasks", "/tasks", VaadinIcon.LIST.create()));
//                new SideNavItem("Analytics", "/analytics",
//                        VaadinIcon.CHART.create()));
        return sideNav;
    }

//public final class MainLayout extends AppLayout {
//
//    private final CurrentUser currentUser;
//    private final AuthenticationContext authenticationContext;
//
//    MainLayout(CurrentUser currentUser, AuthenticationContext authenticationContext) {
//        this.currentUser = currentUser;
//        this.authenticationContext = authenticationContext;
//        setPrimarySection(Section.DRAWER);
//        addToDrawer(createHeader(), new Scroller(createSideNav()), createUserMenu());
//    }
//
    private Div createHeader() {
        // TODO Replace with real application logo and name
        var appLogo = VaadinIcon.CUBES.create();
        appLogo.addClassNames(TextColor.PRIMARY, IconSize.LARGE);

        var appName = new Span("acerola track");
        appName.addClassNames(FontWeight.SEMIBOLD, FontSize.LARGE);

        var header = new Div(appLogo, appName);
        header.addClassNames(Display.FLEX, Padding.MEDIUM, Gap.MEDIUM, AlignItems.CENTER);
        return header;
    }
//
//    private SideNav createSideNav() {
//        var nav = new SideNav();
//        nav.addClassNames(Margin.Horizontal.MEDIUM);
//        MenuConfiguration.getMenuEntries().forEach(entry -> nav.addItem(createSideNavItem(entry)));
//        return nav;
//    }
//
//    private SideNavItem createSideNavItem(MenuEntry menuEntry) {
//        if (menuEntry.icon() != null) {
//            return new SideNavItem(menuEntry.title(), menuEntry.path(), new Icon(menuEntry.icon()));
//        } else {
//            return new SideNavItem(menuEntry.title(), menuEntry.path());
//        }
//    }
//
//    private Component createUserMenu() {
//        var user = currentUser.require();
//
//        var avatar = new Avatar(user.getFullName(), user.getPictureUrl());
//        avatar.addThemeVariants(AvatarVariant.LUMO_XSMALL);
//        avatar.addClassNames(Margin.Right.SMALL);
//        avatar.setColorIndex(5);
//
//        var userMenu = new MenuBar();
//        userMenu.addThemeVariants(MenuBarVariant.LUMO_TERTIARY_INLINE);
//        userMenu.addClassNames(Margin.MEDIUM);
//
//        var userMenuItem = userMenu.addItem(avatar);
//        userMenuItem.add(user.getFullName());
//        if (user.getProfileUrl() != null) {
//            userMenuItem.getSubMenu().addItem("View Profile",
//                    event -> UI.getCurrent().getPage().open(user.getProfileUrl()));
//        }
//        // TODO Add additional items to the user menu if needed
//        userMenuItem.getSubMenu().addItem("Logout", event -> authenticationContext.logout());
//
//        // meus dashs
//
//        userMenuItem.getSubMenu().addItem("Meus Dashes", event -> authenticationContext.logout());
//        userMenu.addItem(userMenuItem);
//
//        return userMenu;
//    }
//
}
