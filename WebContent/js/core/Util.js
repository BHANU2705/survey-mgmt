function removeAllChild(node) {
	if (node && node.children && node.children.length > 0) {
		for (var i = 0; i < node.children.length; i++) {
			var child = node.children[i];
			var id = child.id;
			node.removeChild(child);
		}
	}
};