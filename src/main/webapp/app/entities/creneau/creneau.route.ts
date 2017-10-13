import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CreneauComponent } from './creneau.component';
import { CreneauDetailComponent } from './creneau-detail.component';
import { CreneauPopupComponent } from './creneau-dialog.component';
import { CreneauDeletePopupComponent } from './creneau-delete-dialog.component';

export const creneauRoute: Routes = [
    {
        path: 'creneau',
        component: CreneauComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'reservationApp.creneau.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'creneau/:id',
        component: CreneauDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'reservationApp.creneau.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const creneauPopupRoute: Routes = [
    {
        path: 'creneau-new',
        component: CreneauPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'reservationApp.creneau.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'creneau/:id/edit',
        component: CreneauPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'reservationApp.creneau.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'creneau/:id/delete',
        component: CreneauDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'reservationApp.creneau.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
