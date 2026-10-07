workspace {
	model {
		personAlias = Person "A person" "Optional Description"
		systemAlias = softwareSystem "A system" "Optional Description"
		personAlias -> systemAlias "A relationship" "Optional Technology"
	}
	views {
		systemContext systemAlias context_of_systemAlias "Context view of \"A system\"" {
		}
	}
}