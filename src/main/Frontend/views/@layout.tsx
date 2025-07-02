import React from 'react';
import { AppLayout } from '@hilla/react-components/AppLayout';
import {DrawerToggle, Icon, Scroller, SideNav, SideNavItem} from "@vaadin/react-components";

export default function MyAppLayout() {
  return (
      <AppLayout>
          <DrawerToggle slot="navbar" />
          <h1 slot="navbar">
              MyApp
          </h1>
          <Scroller slot="drawer" className="p-s">
              <SideNav>
                  <SideNavItem path="/dashboard">
                      <Icon icon="vaadin:dashboard" slot="prefix" />
                      Dashboard
                  </SideNavItem>
                  <SideNavItem path="/orders">
                      <Icon icon="vaadin:cart" slot="prefix" />
                      Orders
                  </SideNavItem>
                  <SideNavItem path="/customers">
                      <Icon icon="vaadin:user-heart" slot="prefix" />
                      Customers
                  </SideNavItem>
                  <SideNavItem path="/products">
                      <Icon icon="vaadin:package" slot="prefix" />
                      Products
                  </SideNavItem>
                  <SideNavItem path="/documents">
                      <Icon icon="vaadin:records" slot="prefix" />
                      Documents
                  </SideNavItem>
                  <SideNavItem path="/tasks">
                      <Icon icon="vaadin:list" slot="prefix" />
                      Tasks
                  </SideNavItem>
                  <SideNavItem path="/analytics">
                      <Icon icon="vaadin:chart" slot="prefix" />
                      Analytics
                  </SideNavItem>
              </SideNav>
          </Scroller>
      </AppLayout>
  );
}
