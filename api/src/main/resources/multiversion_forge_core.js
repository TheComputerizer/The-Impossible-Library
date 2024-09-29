var CORE = Java.type('mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI');
var LOGGER = Java.type('mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef');

function log(message) {
    LOGGER.logInfo("[Multiversion Transformer (Forge)]: "+message);
}

function initializeCoreMod() {
    var instance = CORE.getInstance();
    log("Initializing "+instance.getCoreInstances().size()+" coremod(s)");
    var ret = {};
    for each(var entryPoint in instance.getCoreInstances()) {
        var id = entryPoint.getCoreID();
        log("Initializing coremod "+id);
        var targets = entryPoint.classTargets();
        log("Adding class targets "+targets);
        ret[id] = {
            "target": {
            "type": "CLASS",
            "names": function(classMap) {
                    log("Calling names function for map "+classMap);
                    var names = [];
                    for each(var target in targets) { names.push(target) }
                    log("Pushing target names "+names);
                    return names;
                }
            },
            "transformer": function(node) {
                log("Transforming node "+node.name);
                entryPoint.editClass(node)
                return node;
            }
        }
    }
    return ret;
}