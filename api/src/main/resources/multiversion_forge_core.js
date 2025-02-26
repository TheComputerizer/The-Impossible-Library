var CORE = Java.type('mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI');
var ENTRYPOINTS= {}; //Something about scope? Idk but the transformers run later
var LOGGER = Java.type('mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef');

function log(message) {
    LOGGER.logInfo("[Multiversion Transformer (Forge)]: "+message);
}

function initializeCoreMod() {
    var instance = CORE.getInstance(); //scope = entire function
    log("Initializing "+instance.getCoreInstances().size()+" coremod(s)");
    var ret = {}; //scope = entire function
    for(let entryPoint in instance.getCoreInstances()) {
        let id = entryPoint.getCoreID();
        log("Initializing "+id);
        ENTRYPOINTS[id] = entryPoint;
        let targets = entryPoint.classTargets();
        ret[id] = {
            "target": {
            "type": "CLASS",
            "names": function(classMap) {
                    let names = [];
                    log("Handling targets for "+id);
                    for(let target in targets) {
                        log("("+id+"): Pushing class target "+target);
                        names.push(target)
                    }
                    return names;
                }
            },
            "transformer": function(node) { //scope isn't constrained to the loop??
                log("Transforming node "+node.name);
                log("id = "+id);
                ENTRYPOINTS[id].editClass(node)
                return node;
            }
        }
    }
    return ret;
}