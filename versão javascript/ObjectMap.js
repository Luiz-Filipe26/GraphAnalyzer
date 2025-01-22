export default class ObjectMap {
    constructor() {
        this.map = new Map();
    }

    set(key, value) {
        const id = key.toStringId();
        this.map.set(id, [key, value]);
    }

    get(key) {
        const id = key.toStringId();
        const entry = this.map.get(id);
        return entry ? entry[1] : undefined;
    }

    has(key) {
        return this.map.has(key.toStringId());
    }

    delete(key) {
        return this.map.delete(key.toStringId());
    }

    getOriginalInstance(key) {
        const id = key.toStringId();
        const entry = this.map.get(id);
        return entry ? entry[0] : undefined;
    }

    *keys() {
        for (const [_, [originalKey, __]] of this.map) {
            yield originalKey;
        }
    }

    *values() {
        for (const [_, [__, value]] of this.map) {
            yield value;
        }
    }

    *entries() {
        for (const [_, [originalKey, value]] of this.map) {
            yield [originalKey, value];
        }
    }

    [Symbol.iterator]() {
        return this.entries();
    }

    clear() {
        this.map.clear();
    }

    size() {
        return this.map.size;
    }

    clone() {
        const clonedMap = new ObjectMap();
        clonedMap.map = new Map(this.map);
        return clonedMap;
    }
}
