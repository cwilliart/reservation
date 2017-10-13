import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ReservationCreneauModule } from './creneau/creneau.module';
import { ReservationReservationModule } from './reservation/reservation.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ReservationCreneauModule,
        ReservationReservationModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ReservationEntityModule {}
