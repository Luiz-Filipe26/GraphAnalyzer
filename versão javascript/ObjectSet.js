export default class ObjectSet {
    constructor() {
        this.map = new Map();
    }

    add(item) {
        const id = item.toStringId();
        if (!this.map.has(id)) {
            this.map.set(id, item);
        }
    }

    has(item) {
        return this.map.has(item.toStringId());
    }

    delete(item) {
        this.map.delete(item.toStringId());
    }

    get(item) {
        return this.map.get(item.toStringId());
    }

    clear() {
        this.map.clear();
    }

    size() {
        return this.map.size;
    }

    *keys() {
        for (const [_, value] of this.map) {
            yield value.toStringId();
        }
    }

    *values() {
        for (const value of this.map.values()) {
            yield value;
        }
    }

    *entries() {
        for (const [_, value] of this.map) {
            yield [value.toStringId(), value];
        }
    }

    [Symbol.iterator]() {
        return this.values();
    }

    clone() {
        const clonedSet = new ObjectSet();
        clonedSet.map = new Map(this.map);
        return clonedSet;
    }
}