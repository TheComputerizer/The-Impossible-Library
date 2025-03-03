var CORE = Java.type('mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI');
var ENTRYPOINTS = {}; //Something about scope? Idk but the transformers run later
var LOGGER = Java.type('mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef');

function log(message) {
    LOGGER.logInfo("[Multiversion Transformer (Forge)]: "+message);
}

function initializeCoreMod() {
    var instance = CORE.getInstance();
    log("Initializing "+instance.getCoreInstances().size()+" coremod(s)");
    var ret = {};
    instance.getCoreInstances().forEach(function(entryPoint) {
        log("Initializing "+entryPoint.getCoreID());
        ENTRYPOINTS[entryPoint.getCoreID()] = entryPoint;
        ret[entryPoint.getCoreID()] = {
            "target": {
            "type": "CLASS",
            "names": function(classMap) {
                    var names = [];
                    log("Handling targets for "+entryPoint.getCoreID());
                    entryPoint.classTargets().forEach(function(target) {
                        log("("+entryPoint.getCoreID()+"): Pushing class target "+target);
                        names.push(target);
                    });
                    return names;
                }
            },
            "transformer": function(node) { //scope isn't constrained to the loop??
                log("Transforming node "+node.name);
                log("id = "+entryPoint.getCoreID());
                ENTRYPOINTS[entryPoint.getCoreID()].editClass(node);
                return node;
            }
        }
    });
    return ret;
}