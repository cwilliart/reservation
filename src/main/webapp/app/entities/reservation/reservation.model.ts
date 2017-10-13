import { BaseEntity, User } from './../../shared';

export class Reservation implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        public descriptif?: string,
        public titre?: string,
        public user?: User,
        public creneaus?: BaseEntity[],
    ) {
    }
}
