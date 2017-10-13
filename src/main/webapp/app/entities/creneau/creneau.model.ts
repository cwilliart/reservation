import { BaseEntity } from './../../shared';

export class Creneau implements BaseEntity {
    constructor(
        public id?: number,
        public heure?: string,
        public reservations?: BaseEntity[],
    ) {
    }
}
