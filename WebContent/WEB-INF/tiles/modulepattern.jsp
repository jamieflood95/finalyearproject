<script>
	var modularpattern = (function() {
		var sum = 0;
		return {
			add : function() {
				sum = sum + 1;
				return sum;
			},
			reset : function() {
				return sum = 0;
			}
		}
	}());
	alert(modularpattern.add());
	alert(modularpattern.add());
	alert(modularpattern.reset());
</script>