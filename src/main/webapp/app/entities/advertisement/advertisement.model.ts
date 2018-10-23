import { BaseEntity } from './../../shared';

export const enum ECategory {
    'WYNIKI',
    'KONKURS',
    'KOLOKWIUM',
    'WYDARZENIE'
}

export const enum ECollege {
    'KOSZALIN',
    'SZCZECIN',
    'WARSZAWA'
}

export const enum EDepartment {
    'WNE',
    'WEII',
    'WM'
}

export class Advertisement implements BaseEntity {
    constructor(
        public id?: number,
        public title?: string,
        public description?: string,
        public date?: string,
        public author?: string,
        public category?: ECategory,
        public college?: ECollege,
        public department?: EDepartment,
        public email?: string,
        public link?: string,
        public pictureContentType?: string,
        public picture?: any,
    ) {
    }
}
