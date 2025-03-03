var CORE = Java.type('mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI');
var LOGGER = Java.type('mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef');

function log(message) {
    LOGGER.logInfo("[Multiversion Transformer (Forge)]: "+message);
}

function initializeCoreMod() {
    var instance = CORE.getInstance();
    log("Initializing "+instance.getCoreInstances().size()+" coremod(s)");
    var ret = {};
    instance.getCoreInstances().forEach(function(entryPoint) {
        log("<"+entryPoint.getCoreName()+">: Initializing with ID `"+entryPoint.getCoreID()+"`");
        ret[entryPoint.getCoreID()] = {
            "target": {
            "type": "CLASS",
            "names": function(classMap) {
                    var names = [];
                    var size = entryPoint.classTargets().size();
                    log("<"+entryPoint.getCoreName()+">: Requesting transformation of "+size+" classes");
                    entryPoint.classTargets().forEach(function(target) {
                        names.push(target);
                    });
                    return names;
                }
            },
            "transformer": function(node) {
                log("<"+entryPoint.getCoreName()+">: Requested transformation of "+node.name);
                entryPoint.editClass(node);
                return node;
            }
        }
    });
    return ret;
}