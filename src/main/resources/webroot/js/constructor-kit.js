'use strict';
var ck, create, mixin,
  __slice = [].slice,
  __hasProp = {}.hasOwnProperty;

create = Object.create || function(o) {
  var F;
  F = (function() {
    function F() {
      this.constructor = F;
    }

    return F;

  })();
  F.prototype = o;
  return new F;
};

mixin = function() {
  var dest, k, obj, objs, v, _i, _len;
  dest = arguments[0], objs = 2 <= arguments.length ? __slice.call(arguments, 1) : [];
  for (_i = 0, _len = objs.length; _i < _len; _i++) {
    obj = objs[_i];
    for (k in obj) {
      if (!__hasProp.call(obj, k)) continue;
      v = obj[k];
      dest[k] = v;
    }
  }
  return dest;
};

ck = function(constructor, prototypeProperties, prototypeChain) {
  if (arguments.length === 1) {
    prototypeProperties = {};
  } else if (arguments.length === 2) {
    prototypeChain = prototypeProperties;
    prototypeProperties = {};
  }
  prototypeProperties = prototypeProperties || {};
  if (typeof prototypeChain === 'function') {
    constructor.prototype = create(prototypeChain.prototype);
    mixin(constructor.prototype, prototypeProperties);
  } else if (prototypeChain) {
    constructor.prototype = create(prototypeChain);
    mixin(constructor.prototype, prototypeProperties);
  } else {
    constructor.prototype = mixin({}, prototypeProperties);
  }
  constructor.prototype.constructor = constructor;
  return constructor;
};

ck.create = create;

ck.mixin = mixin;

ck.ck = ck;

ck.constructorKit = ck;

module.exports = ck;
