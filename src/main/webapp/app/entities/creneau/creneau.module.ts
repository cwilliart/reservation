import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ReservationSharedModule } from '../../shared';
import {
    CreneauService,
    CreneauPopupService,
    CreneauComponent,
    CreneauDetailComponent,
    CreneauDialogComponent,
    CreneauPopupComponent,
    CreneauDeletePopupComponent,
    CreneauDeleteDialogComponent,
    creneauRoute,
    creneauPopupRoute,
} from './';

const ENTITY_STATES = [
    ...creneauRoute,
    ...creneauPopupRoute,
];

@NgModule({
    imports: [
        ReservationSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CreneauComponent,
        CreneauDetailComponent,
        CreneauDialogComponent,
        CreneauDeleteDialogComponent,
        CreneauPopupComponent,
        CreneauDeletePopupComponent,
    ],
    entryComponents: [
        CreneauComponent,
        CreneauDialogComponent,
        CreneauPopupComponent,
        CreneauDeleteDialogComponent,
        CreneauDeletePopupComponent,
    ],
    providers: [
        CreneauService,
        CreneauPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ReservationCreneauModule {}
